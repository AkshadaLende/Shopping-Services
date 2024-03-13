package com.coding.tech.orderservice.service;

import com.coding.tech.orderservice.dto.OrderRequest;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {

  public void placeOrder(OrderRequest orderRequest);
}
