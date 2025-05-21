package com.example.fineservice.infrastructure.persistence.jpa;

import com.example.fineservice.domain.model.values.FineStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "fine")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class FineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long loanId;
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private FineStatus status;
    private String description;
}
