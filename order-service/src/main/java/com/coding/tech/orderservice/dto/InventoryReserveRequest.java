package com.coding.tech.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReserveRequest {
    private List<OrderLineItemsDto> items;
    private String sagaId;
}
