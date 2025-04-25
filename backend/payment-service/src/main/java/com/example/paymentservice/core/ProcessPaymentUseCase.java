package com.example.paymentservice.core;

import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.PaymentCreationDate;
import com.example.paymentservice.domain.model.PaymentProcessRequest;
import com.example.paymentservice.domain.model.PaymentStatus;
import com.example.paymentservice.domain.ports.PaymentStrategyPort;
import com.example.paymentservice.domain.request.PaymentRequest;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class ProcessPaymentUseCase {
    private final PaymentRequestFactory paymentRequestFactory;
    private final PaymentPortFactory paymentPortFactory;
    private final PaymentService paymentService;

    Payment execute(PaymentProcessRequest request) {
        PaymentRequest paymentRequest = paymentRequestFactory.create(request);
        PaymentStrategyPort<PaymentRequest> paymentStrategy = paymentPortFactory.getPaymentStrategy(paymentRequest);
        boolean success = paymentStrategy.processPayment(paymentRequest);

        Payment payment = Payment.builder()
                .amount(request.getAmount())
                .creationDate(new PaymentCreationDate(LocalDateTime.now()))
                .userId(request.getUserId())
                .description(request.getDescription())
                .method(request.getMethod())
                .status(success ? PaymentStatus.SUCCESS : PaymentStatus.FAILED)
                .build();

        return paymentService.save(payment);
    }
}
