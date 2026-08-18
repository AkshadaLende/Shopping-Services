package com.programmingtechie.inventoryservice.model;

import com.programmingtechie.inventoryservice.dto.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "t_inventory_transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String sagaId;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;   // RESERVED, RELEASED, FAILED

    private LocalDateTime createdAt;

}
