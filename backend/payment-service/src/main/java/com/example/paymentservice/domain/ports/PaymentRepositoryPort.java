package com.example.paymentservice.domain.ports;

import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.PaymentId;
import com.example.paymentservice.domain.model.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PaymentRepositoryPort {

    Page<Payment> findAllByUserId(UserId userId, Pageable pageable);

    Optional<Payment> findById(PaymentId id);

    Payment save(Payment payment);
}
