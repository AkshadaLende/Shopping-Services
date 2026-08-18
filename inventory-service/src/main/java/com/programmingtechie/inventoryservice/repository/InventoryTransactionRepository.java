package com.programmingtechie.inventoryservice.repository;

import com.programmingtechie.inventoryservice.model.InventoryTransaction;
import com.programmingtechie.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryTransactionRepository extends JpaRepository<InventoryTransaction, Long> {
    Optional<InventoryTransaction> findBySagaId(String sagaId);
}
