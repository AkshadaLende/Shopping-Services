package com.shopping.notificationservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCompletedEvent {
    private String orderNumber;
    private Long userId;
    private Double amount;
    private String sagaId;
}
