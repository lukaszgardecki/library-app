package com.example.libraryapp.application.payment;

import com.example.libraryapp.domain.payment.model.Payment;
import com.example.libraryapp.domain.payment.model.PaymentId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetPaymentUseCase {
    private final PaymentService paymentService;

    Payment execute(PaymentId id) {
        return paymentService.getPaymentById(id);
    }
}
