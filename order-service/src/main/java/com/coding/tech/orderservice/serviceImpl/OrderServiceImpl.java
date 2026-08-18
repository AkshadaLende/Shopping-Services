package com.coding.tech.orderservice.serviceImpl;

import com.coding.tech.orderservice.Repository.OrderRepository;
import com.coding.tech.orderservice.dto.InventoryResponse;
import com.coding.tech.orderservice.dto.OrderLineItemsDto;
import com.coding.tech.orderservice.dto.OrderRequest;
import com.coding.tech.orderservice.entity.Order;
import com.coding.tech.orderservice.entity.OrderLineItems;
import com.coding.tech.orderservice.entity.OrderStatus;
import com.coding.tech.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Transactional
    @Override
    public Order createOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setUserId(orderRequest.getUserId());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCreatedDt(LocalDateTime.now());
        order.setUpdatedDt(LocalDateTime.now());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(dto -> mapToOrderLineItems(dto, order))
                .collect(Collectors.toList());

        order.setOrderLineItems(orderLineItems);

        return orderRepository.save(order);
    }

    private OrderLineItems mapToOrderLineItems(OrderLineItemsDto dto, Order order) {
        OrderLineItems item = new OrderLineItems();
        item.setSkuCode(dto.getSkuCode());
        item.setPrice(dto.getPrice());
        item.setQuantity(dto.getQuantity());
        item.setOrder(order);
        return item;
    }
}
