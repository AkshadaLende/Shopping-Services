package com.coding.tech.orderservice.entity;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="t_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @Column(unique = true, nullable = false)
    private String OrderNumber;

    @OneToMany(mappedBy = "order", cascade=CascadeType.ALL, orphanRemoval = true)
    private List<OrderLineItems> orderLineItems;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime createdDt;
    private LocalDateTime updatedDt;

}
