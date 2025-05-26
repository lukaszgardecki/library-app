package com.example.paymentservice.infrastructure.persistence.jpa;

import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.values.*;
import com.example.paymentservice.domain.ports.out.PaymentRepositoryPort;
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
    public Page<Payment> findAllByUserId(UserId userId, Pageable pageable) {
        return repository.findAllByParams(userId.value(), pageable)
                .map(this::toModel);
    }

    @Override
    public Optional<Payment> findById(PaymentId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    public Payment save(Payment payment) {
        PaymentEntity savedPayment = repository.save(toEntity(payment));
        return toModel(savedPayment);
    }

    private PaymentEntity toEntity(Payment model) {
        return PaymentEntity.builder()
                .id(model.getId() != null ? model.getId().value() : null)
                .amount(model.getAmount().value())
                .creationDate(model.getCreationDate().value())
                .userId(model.getUserId().value())
                .description(model.getDescription().value())
                .method(model.getMethod())
                .status(model.getStatus())
                .build();
    }

    private Payment toModel(PaymentEntity entity) {
        return Payment.builder()
                .id(new PaymentId(entity.getId()))
                .amount(new PaymentAmount(entity.getAmount()))
                .creationDate(new PaymentCreationDate(entity.getCreationDate()))
                .userId(new UserId(entity.getUserId()))
                .description(new PaymentDescription(entity.getDescription()))
                .method(entity.getMethod())
                .status(entity.getStatus())
                .build();
    }
}
