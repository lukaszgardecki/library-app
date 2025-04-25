package com.example.paymentservice.core;

import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.PaymentId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetPaymentUseCase {
    private final PaymentService paymentService;

    Payment execute(PaymentId id) {
        return paymentService.getPaymentById(id);
    }
}
