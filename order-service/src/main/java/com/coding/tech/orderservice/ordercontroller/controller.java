package com.coding.tech.orderservice.ordercontroller;

import com.coding.tech.orderservice.dto.OrderRequest;
import com.coding.tech.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class controller {

    private final OrderService orderService;
   //create order
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createOrder(@RequestBody OrderRequest orderRequest){
        orderService.placeOrder(orderRequest);
        return "Order place Successfully ";
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
