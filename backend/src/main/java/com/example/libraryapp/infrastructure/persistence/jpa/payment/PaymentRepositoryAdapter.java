package com.example.libraryapp.infrastructure.persistence.jpa.payment;

import com.example.libraryapp.domain.payment.model.Payment;
import com.example.libraryapp.domain.payment.ports.PaymentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class PaymentRepositoryAdapter implements PaymentRepositoryPort {
    private final JpaPaymentRepository repository;

    @Override
    public Page<Payment> findAllByUserId(Long userId, Pageable pageable) {
        return repository.findAllByParams(userId, pageable)
                .map(this::toModel);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity savedPayment = repository.save(toEntity(payment));
        return toModel(savedPayment);
    }

    private PaymentEntity toEntity(Payment model) {
        return PaymentEntity.builder()
                .id(model.getId())
                .amount(model.getAmount())
                .creationDate(model.getCreationDate())
                .userId(model.getUserId())
                .description(model.getDescription())
                .method(model.getMethod())
                .status(model.getStatus())
                .build();
    }

    private Payment toModel(PaymentEntity entity) {
        return Payment.builder()
                .id(entity.getId())
                .amount(entity.getAmount())
                .creationDate(entity.getCreationDate())
                .userId(entity.getUserId())
                .description(entity.getDescription())
                .method(entity.getMethod())
                .status(entity.getStatus())
                .build();
    }
}
