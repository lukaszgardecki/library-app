package com.example.libraryapp.domain.payment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Payment {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime creationDate;
    private Long userId;
    private String description;
    private PaymentMethod method;
    private PaymentStatus status;
}
