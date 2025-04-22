package com.example.libraryapp.domain.payment.dto;


import com.example.libraryapp.domain.payment.model.PaymentMethod;
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
