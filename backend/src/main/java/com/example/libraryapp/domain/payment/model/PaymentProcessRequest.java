package com.example.libraryapp.domain.payment.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PaymentProcessRequest {
    private BigDecimal amount;
    private Long userId;
    private String description;
    private PaymentMethod method;
    private PaymentCardDetails cardPaymentDetails;
}
