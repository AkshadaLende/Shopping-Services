package com.coding.tech.orderservice.dto;


import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

   private List<OrderLineItemsDto> orderLineItemsDto;
}
