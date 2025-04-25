package com.example.paymentservice.domain.dto;


import com.example.paymentservice.domain.model.PaymentMethod;
import lombok.*;

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
