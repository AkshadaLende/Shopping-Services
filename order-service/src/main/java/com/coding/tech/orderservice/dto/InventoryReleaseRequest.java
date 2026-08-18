package com.coding.tech.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryReleaseRequest {
    private String id;
    private List<OrderLineItemsDto> orderLineItemsDto;
    public InventoryReleaseRequest(List<OrderLineItemsDto> orderLineItemsDtoList, String sagaId) {
        this.orderLineItemsDto = orderLineItemsDtoList;
        this.id = sagaId;
    }
}
