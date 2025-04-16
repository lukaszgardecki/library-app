package com.example.libraryapp.application.payment;

import com.example.libraryapp.domain.payment.model.Payment;
import com.example.libraryapp.domain.payment.model.PaymentCreationDate;
import com.example.libraryapp.domain.payment.model.PaymentProcessRequest;
import com.example.libraryapp.domain.payment.model.PaymentStatus;
import com.example.libraryapp.domain.payment.ports.PaymentStrategyPort;
import com.example.libraryapp.domain.payment.request.PaymentRequest;
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
