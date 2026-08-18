package com.shopping.notificationservice.service;

import com.shopping.notificationservice.event.PaymentCompletedEvent;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendPaymentConfirmation(PaymentCompletedEvent event) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo("user-" + event.getUserId() + "@example.com");  // replace with real lookup via user-service
        message.setSubject("Payment confirmed for order " + event.getOrderNumber());
        message.setText("Your payment of $" + event.getAmount() +
                " for order " + event.getOrderNumber() + " was successful. Thank you for shopping with us!");

        mailSender.send(message);
    }
}
