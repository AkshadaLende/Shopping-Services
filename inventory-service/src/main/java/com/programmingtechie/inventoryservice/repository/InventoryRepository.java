package com.programmingtechie.inventoryservice.repository;

import java.util.List;
import java.util.Optional;

import com.programmingtechie.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {

   // Optional<Inventory> findBySkuCode(String skuCode);

   List<Inventory> findBySkuCodeIn(List<String> skucode);
}
