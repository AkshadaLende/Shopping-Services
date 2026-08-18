package com.shopping.notificationservice.listener;

import com.shopping.notificationservice.event.PaymentCompletedEvent;
import com.shopping.notificationservice.service.EmailService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    private static final Logger log = LoggerFactory.getLogger(PaymentEventListener.class);

    private final EmailService emailService;

    public PaymentEventListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "payment-completed", groupId = "notification-service",
            containerFactory = "kafkaListenerContainerFactory")
    public void handlePaymentCompleted(ConsumerRecord<String, PaymentCompletedEvent> record,
                                       Acknowledgment ack) {
        PaymentCompletedEvent event = record.value();
        log.info("Received PaymentCompletedEvent for order {}", event.getOrderNumber());

        try {
            emailService.sendPaymentConfirmation(event);
            ack.acknowledge();   // commit offset ONLY after email is actually sent
        } catch (Exception ex) {
            log.error("Failed to send email for order {}, will retry on redelivery", event.getOrderNumber(), ex);
            // don't acknowledge — Kafka redelivers this message on next poll
        }
    }
}