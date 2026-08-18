package com.coding.tech.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {

   private boolean isSuccess = false;

   public boolean isSucess() {
      return true;
   }
}
