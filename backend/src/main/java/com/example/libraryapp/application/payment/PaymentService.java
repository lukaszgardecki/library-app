package com.example.libraryapp.application.payment;

import com.example.libraryapp.domain.payment.exceptions.PaymentNotFoundException;
import com.example.libraryapp.domain.payment.model.Payment;
import com.example.libraryapp.domain.payment.model.PaymentId;
import com.example.libraryapp.domain.payment.ports.PaymentRepositoryPort;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class PaymentService {
    private final PaymentRepositoryPort repository;

    Page<Payment> getAllByUserId(UserId userId, Pageable pageable) {
        return repository.findAllByUserId(userId, pageable);
    }

    Payment getPaymentById(PaymentId id) {
        return repository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException(id));
    }

    Payment save(Payment payment) {
        return repository.save(payment);
    }
}
