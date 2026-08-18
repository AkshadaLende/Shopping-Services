package com.shopping.paymentservice.controller;

import com.shopping.paymentservice.dto.PaymentRequest;
import com.shopping.paymentservice.dto.PaymentResponse;
import com.shopping.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/charge")
    public ResponseEntity<PaymentResponse> charge(@RequestBody PaymentRequest request) {
        return ResponseEntity.ok(paymentService.charge(request));
    }
}
