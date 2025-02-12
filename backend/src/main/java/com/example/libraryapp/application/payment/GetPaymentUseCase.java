package com.example.libraryapp.application.payment;

import com.example.libraryapp.domain.payment.model.Payment;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetPaymentUseCase {
    private final PaymentService paymentService;

    Payment execute(Long id) {
        return paymentService.getPaymentById(id);
    }
}
