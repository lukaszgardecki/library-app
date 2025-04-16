package com.example.libraryapp.application.payment;

import com.example.libraryapp.domain.payment.model.Payment;
import com.example.libraryapp.domain.user.model.UserId;
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
