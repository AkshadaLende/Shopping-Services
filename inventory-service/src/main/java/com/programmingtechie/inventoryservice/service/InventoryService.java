package com.programmingtechie.inventoryservice.service;

import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import lombok.RequiredArgsConstructor;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
   // public  InventoryService(InventoryRepository inventoryRepository){
    //    this.inventoryRepository=inventoryRepository;
  //  }
    @Transactional(readOnly=true)
    public List<InventoryResponse> isStock(List<String> skucode){
        return  inventoryRepository.findBySkuCodeIn(skucode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skucode(inventory.getSkuCode())
                            .isStock(inventory.getQuantity()>0)
                            .build()
                            ).toList();



    }
}
