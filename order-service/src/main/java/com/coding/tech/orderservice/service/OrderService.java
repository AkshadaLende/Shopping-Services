package com.coding.tech.orderservice.service;

import com.coding.tech.orderservice.dto.OrderRequest;
import com.coding.tech.orderservice.entity.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

  public Order createOrder(OrderRequest orderRequest);
}
