package com.example.paymentservice.core;

import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.PaymentId;
import com.example.paymentservice.domain.ports.SourceValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetPaymentUseCase {
    private final PaymentService paymentService;
    private final SourceValidator sourceValidator;

    Payment execute(PaymentId id) {
        Payment payment = paymentService.getPaymentById(id);
        sourceValidator.validateUserIsOwner(payment.getUserId());
        return payment;
    }
}
