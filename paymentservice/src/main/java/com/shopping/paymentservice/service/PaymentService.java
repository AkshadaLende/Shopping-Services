package com.shopping.paymentservice.service;


import com.shopping.paymentservice.dto.PaymentRequest;
import com.shopping.paymentservice.dto.PaymentResponse;
import com.shopping.paymentservice.entity.PaymentStatus;
import com.shopping.paymentservice.entity.PaymentTransaction;
import com.shopping.paymentservice.event.PaymentCompletedEvent;
import com.shopping.paymentservice.repository.PaymentTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentTransactionRepository transactionRepository;
    private static final String TOPIC = "payment-completed";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Transactional
    public PaymentResponse charge(PaymentRequest request) {

        // Idempotency: same sagaId retried → return the original result, don't charge twice
        var existing = transactionRepository.findBySagaId(request.getSagaId());
        if (existing.isPresent()) {
            PaymentTransaction txn = existing.get();
            return new PaymentResponse(
                    txn.getStatus() == PaymentStatus.SUCCESS,
                    "Already processed: " + txn.getStatus()
            );
        }

        // Simulated payment gateway call — replace with real gateway integration later
        boolean approved = simulateCharge(request.getAmount());

        PaymentTransaction txn = new PaymentTransaction();
        txn.setOrderNumber(request.getOrderNumber());
        txn.setUserId(request.getUserId());
        txn.setAmount(request.getAmount());
        txn.setSagaId(request.getSagaId());
        txn.setStatus(approved ? PaymentStatus.SUCCESS : PaymentStatus.DECLINED);
        txn.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(txn);
        if (approved) {
            PaymentCompletedEvent event = new PaymentCompletedEvent(
                    request.getOrderNumber(), request.getUserId(), request.getAmount(), request.getSagaId());

            // key = sagaId → guarantees ordering for events tied to the same order
            kafkaTemplate.send(TOPIC, request.getSagaId(), event);
        }

        return new PaymentResponse(approved, approved ? "Payment successful" : "Payment declined");
    }

    private boolean simulateCharge(Double amount) {
        // placeholder logic — swap with Stripe/Razorpay/etc. integration
        return amount != null && amount > 0;
    }
}
