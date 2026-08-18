package com.programmingtechie.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReleaseRequest {

    private List<OrderLineItemsDto> items;
    private String sagaId;
}
