package com.coding.tech.orderservice.exception;

public class PaymentFailedException extends Throwable {
    public PaymentFailedException(String paymentDeclined) {
        super(paymentDeclined);
    }
}
