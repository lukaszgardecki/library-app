package com.example.libraryapp.domain.payment.ports;

import com.example.libraryapp.domain.payment.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PaymentRepository {

    Page<Payment> findAllByUserId(Long userId, Pageable pageable);

    Optional<Payment> findById(Long id);

    Payment save(Payment payment);
}
