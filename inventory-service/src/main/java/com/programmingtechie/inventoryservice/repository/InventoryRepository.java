package com.programmingtechie.inventoryservice.repository;

import java.util.List;
import java.util.Optional;

import com.programmingtechie.inventoryservice.model.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

   // Optional<Inventory> findBySkuCode(String skuCode);

   List<Inventory> findBySkuCodeIn(List<String> skucode);
   // SELECT ... FOR UPDATE — locks the row until the transaction commits,
   // so two concurrent orders can't both read the same availableQuantity
   @Lock(LockModeType.PESSIMISTIC_WRITE)
   @Query("SELECT i FROM Inventory i WHERE i.skuCode = :skuCode")
   Optional<Inventory> findBySakuCodeForUpdate(@Param("skuCode") String skuCode);
}
