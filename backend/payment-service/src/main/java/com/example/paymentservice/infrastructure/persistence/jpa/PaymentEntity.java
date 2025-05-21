package com.example.paymentservice.infrastructure.persistence.jpa;

import com.example.paymentservice.domain.model.values.PaymentMethod;
import com.example.paymentservice.domain.model.values.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class PaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private LocalDateTime creationDate;
    private Long userId;
    private String description;

    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}
