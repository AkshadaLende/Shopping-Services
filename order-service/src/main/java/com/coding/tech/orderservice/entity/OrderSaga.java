package com.coding.tech.orderservice.entity;

import com.coding.tech.orderservice.dto.SagaStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_saga")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String orderId;
    private String sagaId;

    @Enumerated(EnumType.STRING)
    private SagaStatus sagaStatus;

    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;
}
