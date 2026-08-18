package com.coding.tech.orderservice.entity;

public enum OrderStatus {
    PENDING, //just created, saga in progress
    CONFIRMED, //saga completed successfully
    CANCELLED, // saga failed, compensations run
    FAILED // unexpected/system failure
}
