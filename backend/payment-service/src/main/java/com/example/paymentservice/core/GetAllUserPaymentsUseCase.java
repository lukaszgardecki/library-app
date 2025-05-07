package com.example.paymentservice.core;

import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.values.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetAllUserPaymentsUseCase {
    private final PaymentService paymentService;

    Page<Payment> execute(UserId userId, Pageable pageable) {
        return paymentService.getAllByUserId(userId, pageable);
    }
}
