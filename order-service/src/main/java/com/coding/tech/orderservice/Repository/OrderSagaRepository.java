package com.coding.tech.orderservice.Repository;

import com.coding.tech.orderservice.entity.OrderSaga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderSagaRepository extends JpaRepository<OrderSaga, Long> {
}
