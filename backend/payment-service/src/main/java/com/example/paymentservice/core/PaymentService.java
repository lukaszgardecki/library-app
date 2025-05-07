package com.example.paymentservice.core;

import com.example.paymentservice.domain.exceptions.PaymentNotFoundException;
import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.values.PaymentId;
import com.example.paymentservice.domain.model.values.UserId;
import com.example.paymentservice.domain.ports.out.PaymentRepositoryPort;
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
