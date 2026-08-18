package com.coding.tech.orderservice.exception;

public class InventoryReservationException extends Exception {
    public InventoryReservationException(String productIsNotInStock) {
        super(productIsNotInStock);
    }
}
