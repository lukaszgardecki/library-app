package com.example.libraryapp.application.payment;

import com.example.libraryapp.domain.payment.exceptions.PaymentNotFoundException;
import com.example.libraryapp.domain.payment.model.Payment;
import com.example.libraryapp.domain.payment.ports.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class PaymentService {
    private final PaymentRepository repository;

    Page<Payment> getAllByUserId(Long userId, Pageable pageable) {
        return repository.findAllByUserId(userId, pageable);
    }

    Payment getPaymentById(Long id) {
        return repository.findById(id)
                .orElseThrow(PaymentNotFoundException::new);
    }

    Payment save(Payment payment) {
        return repository.save(payment);
    }
}
