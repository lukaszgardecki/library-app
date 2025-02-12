package com.example.libraryapp.domain.payment.request;

import com.example.libraryapp.domain.payment.model.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public abstract class PaymentRequest {
    private BigDecimal amount;
    private Long userId;
    private String description;
    private PaymentMethod method;
}
