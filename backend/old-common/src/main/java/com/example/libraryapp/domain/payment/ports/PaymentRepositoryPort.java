package com.example.libraryapp.domain.payment.ports;

import com.example.libraryapp.domain.payment.model.Payment;
import com.example.libraryapp.domain.payment.model.PaymentId;
import com.example.libraryapp.domain.user.model.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PaymentRepositoryPort {

    Page<Payment> findAllByUserId(UserId userId, Pageable pageable);

    Optional<Payment> findById(PaymentId id);

    Payment save(Payment payment);
}
