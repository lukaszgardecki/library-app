package com.example.paymentservice.core;

import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.values.PaymentId;
import com.example.paymentservice.domain.ports.out.SourceValidatorPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetPaymentUseCase {
    private final PaymentService paymentService;
    private final SourceValidatorPort sourceValidator;

    Payment execute(PaymentId id) {
        Payment payment = paymentService.getPaymentById(id);
        sourceValidator.validateUserIsOwner(payment.getUserId());
        return payment;
    }
}
