package com.programmingtechie.inventoryservice.controller;


import com.programmingtechie.inventoryservice.dto.InventoryResponse;
import com.programmingtechie.inventoryservice.service.InventoryService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
// http://localhost:8082/api/inventory/iphone-13,iphone-red    @GetMapping("/{sku-code}")  @PathVariable("sku-code")

    //http://localhost:8082/api/inventory?skucode=iphone13-red&skucode=iphone13-white

    //got 400 bad request here beacause i havent write requestparam("skuCode")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isStock(@RequestParam("skuCode") List<String> skucode){
        return inventoryService.isStock(skucode);

    }
}
