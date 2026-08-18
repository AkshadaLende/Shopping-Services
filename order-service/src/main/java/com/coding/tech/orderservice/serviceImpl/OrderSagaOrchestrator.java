package com.coding.tech.orderservice.serviceImpl;

import com.coding.tech.orderservice.Repository.OrderRepository;
import com.coding.tech.orderservice.Repository.OrderSagaRepository;
import com.coding.tech.orderservice.config.WebClientConfig;
import com.coding.tech.orderservice.dto.*;
import com.coding.tech.orderservice.entity.Order;
import com.coding.tech.orderservice.entity.OrderSaga;
import com.coding.tech.orderservice.entity.OrderStatus;
import com.coding.tech.orderservice.exception.InventoryReservationException;
import com.coding.tech.orderservice.exception.PaymentFailedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderSagaOrchestrator {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderSagaRepository orderSagaRepository;

    @Autowired
    private WebClientConfig webClientConfig;

    public OrderResponse processOrderSaga(Order order, OrderRequest orderRequest) {
        String sagaId = UUID.randomUUID().toString();
        OrderSaga saga = startSaga(order.getId(), sagaId);

        try {
            reserveInventory(orderRequest, saga);
            processPayment(order, orderRequest, saga);
            confirmOrder(order, saga);
            
            return new OrderResponse(order.getOrderNumber(), "CONFIRMED", "Order Place successfully");
        } catch (InventoryReservationException e) {
            log.error("Inventory reservation failed for saga {}" , sagaId, e);
            cancelOrder(order, saga);
            return new OrderResponse(order.getOrderNumber(), "FAILED", "Product is not in stock");
        } catch (PaymentFailedException p) {
            log.error("Payment failed for saga {}", sagaId, p);
            compensateInventory(orderRequest, saga);
            cancelOrder(order, saga);
            return new OrderResponse(order.getOrderNumber(), "FAILED", "Payment Declined");
        }
    }

    private void compensateInventory(OrderRequest orderRequest, OrderSaga saga) {
        try {
            webClientConfig.webclientBuilder().build()
                    .post()
                    .uri("http://inventory-service/api/inventory/release")
                    .bodyValue(new InventoryReleaseRequest(orderRequest.getOrderLineItemsDtoList(), saga.getSagaId()))
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();

            saga.setSagaStatus(SagaStatus.INVENTORY_RELEASED);
            orderSagaRepository.save(saga);
        } catch (Exception e) {
            log.error("CRITICAL: Compensation failed for saga {}", saga.getSagaId());
        }
    }

    private void cancelOrder(Order order, OrderSaga saga) {
        order.setOrderStatus(OrderStatus.CANCELLED);
        order.setUpdatedDt(LocalDateTime.now());
        orderRepository.save(order);

        saga.setSagaStatus(SagaStatus.ORDER_CANCELLED);
        orderSagaRepository.save(saga);
    }

    private void confirmOrder(Order order, OrderSaga saga) {
        order.setOrderStatus(OrderStatus.CONFIRMED);
        order.setUpdatedDt(LocalDateTime.now());
        orderRepository.save(order);

        saga.setSagaStatus(SagaStatus.ORDER_CONFIRMED);
        orderSagaRepository.save(saga);

    }

    private void processPayment(Order order, OrderRequest orderRequest, OrderSaga saga) throws PaymentFailedException {
        PaymentResponse paymentResponse = webClientConfig.webclientBuilder().build()
                .post()
                .uri("http://payment-service/api/payment/charge")
                .bodyValue(new PaymentRequest(order.getOrderNumber(), order.getUserId(), saga.getSagaId()))
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .block();

         if (paymentResponse == null || !paymentResponse.isSucess()) {
             throw new PaymentFailedException("Payment declined");
         }

         saga.setSagaStatus(SagaStatus.PAYMENT_COMPLETED);
         orderSagaRepository.save(saga);
    }

    private void reserveInventory(OrderRequest orderRequest, OrderSaga saga) throws InventoryReservationException {
        InventoryResponse[] inventoryResponses = webClientConfig.webclientBuilder().build()
                .post()
                .uri("http://inventory-service/api/inventory/reserve")
                .bodyValue(new InventoryReserveRequest(orderRequest.getOrderLineItemsDtoList(), saga.getSagaId()))
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allReserved = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::isStock);
        if (!allReserved) {
            throw new InventoryReservationException("Product is not in stock");
        }

        saga.setSagaStatus(SagaStatus.INVENTORY_RESERVED);
    }

    private OrderSaga startSaga(Long id, String sagaId) {
        OrderSaga orderSaga = new OrderSaga();
        orderSaga.setId(Long.valueOf(sagaId));
        orderSaga.setOrderId(String.valueOf(id));
        orderSaga.setSagaStatus(SagaStatus.STARTED);
        orderSaga.setCreatedDt(LocalDateTime.now());
        return orderSagaRepository.save(orderSaga);
    }

}
