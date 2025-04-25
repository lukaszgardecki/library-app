package com.example.fineservice.domain.dto;

import com.example.fineservice.domain.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PaymentProcessRequestDto {
    private BigDecimal amount;
    private Long userId;
    private String description;
    private PaymentMethod method;
    private PaymentCardDetailsDto cardPaymentDetails;
}
