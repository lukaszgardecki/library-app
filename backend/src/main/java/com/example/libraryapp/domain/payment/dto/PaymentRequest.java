package com.example.libraryapp.domain.payment.dto;

import com.example.libraryapp.domain.payment.PaymentDescription;
import com.example.libraryapp.domain.payment.PaymentMethod;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long memberId;
    private BigDecimal amount;
    private PaymentDescription paymentDescription;
    private PaymentMethod paymentMethod;
}
