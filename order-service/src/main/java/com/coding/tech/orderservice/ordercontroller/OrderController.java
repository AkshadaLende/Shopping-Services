package com.coding.tech.orderservice.ordercontroller;

import com.coding.tech.orderservice.dto.OrderRequest;
import com.coding.tech.orderservice.dto.OrderResponse;
import com.coding.tech.orderservice.entity.Order;
import com.coding.tech.orderservice.service.OrderService;
import com.coding.tech.orderservice.serviceImpl.OrderSagaOrchestrator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderSagaOrchestrator orderSagaOrchestrator;

    //create order
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);

        OrderResponse response = new OrderResponse(order.getOrderNumber(),
                order.getOrderStatus().name(), "Order created, processing saga");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(orderRequest);
        OrderResponse response = orderSagaOrchestrator.processOrderSaga(order, orderRequest);

        HttpStatus httpStatus = "CONFIRMED".equals(response.getStatus()) ? HttpStatus.CREATED : HttpStatus.CONFLICT;
        return ResponseEntity.status(httpStatus).body(response);
    }
    //update order
//    public ResponseEntity<> updateOrder(@RequestBody OrderRequest orderRequest){
//        orderService.updateOrder();
//        return
//    }
    //cancel order
    //show order status
    //order list


}
