package com.programmingtechie.inventoryservice.service;

import com.programmingtechie.inventoryservice.dto.InventoryReserveRequest;
import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.dto.OrderLineItemsDto;
import com.programmingtechie.inventoryservice.dto.TransactionStatus;
import com.programmingtechie.inventoryservice.model.Inventory;
import com.programmingtechie.inventoryservice.model.InventoryTransaction;
import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import com.programmingtechie.inventoryservice.repository.InventoryTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;

    // public  InventoryService(InventoryRepository inventoryRepository){
    //    this.inventoryRepository=inventoryRepository;
    //  }
    @Transactional(readOnly = true)
    public List<InventoryResponse> isStock(List<String> skucode) {
        return inventoryRepository.findBySkuCodeIn(skucode).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .skucode(inventory.getSkuCode())
                                .isStock(inventory.getAvailableQuantity() > 0)
                                .build()
                ).toList();


    }

    public List<InventoryResponse> reserve(InventoryReserveRequest request) {
        // Idempotency check first — if this sagaId already reserved, don't touch stock again
        Optional<InventoryTransaction> sagaId = inventoryTransactionRepository.findBySagaId(request.getSagaId());
        if (sagaId.isPresent() && sagaId.get().getStatus().equals(TransactionStatus.RESERVED)) {
            return request.getOrderLineItemsDtoList().stream()
                    .map(item -> new InventoryResponse(item.getSkuCode(), true))
                    .toList();
        }

        List<InventoryResponse> response = request.getOrderLineItemsDtoList().stream().map(this::reserveSingleItem).toList();
        boolean allReserve = response.stream().allMatch(InventoryResponse::isStock);

        InventoryTransaction inventoryTransaction = new InventoryTransaction();
        inventoryTransaction.setSagaId(request.getSagaId());
        inventoryTransaction.setStatus(allReserve ? TransactionStatus.RESERVED : TransactionStatus.FAILED);
        inventoryTransaction.setCreatedAt(LocalDateTime.now());
        inventoryTransactionRepository.save(inventoryTransaction);

        return response;
    }

    private InventoryResponse reserveSingleItem(OrderLineItemsDto item) {
        Inventory inventory = inventoryRepository.findBySakuCodeForUpdate(item.getSkuCode())
                .orElseThrow(() -> new IllegalArgumentException("Unknown SKU" + item.getSkuCode()));

        if (inventory.getAvailableQuantity() < item.getQuantity()) {
            return new InventoryResponse(item.getSkuCode(), false);
        }

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() - item.getQuantity());
        inventory.setReservedQuantity(inventory.getReservedQuantity() + item.getQuantity());
        inventoryRepository.save(inventory);
        return new InventoryResponse(item.getSkuCode(), true);
    }
}
