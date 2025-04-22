package com.example.libraryapp.domain.payment.dto;

import com.example.libraryapp.domain.payment.model.PaymentMethod;
import com.example.libraryapp.domain.payment.model.PaymentStatus;
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
public class PaymentDto {
    private Long id;
    private BigDecimal amount;
    private LocalDateTime creationDate;
    private Long userId;
    private String description;
    private PaymentMethod method;
    private PaymentStatus status;
}
