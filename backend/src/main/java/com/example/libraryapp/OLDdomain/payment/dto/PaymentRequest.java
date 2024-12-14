package com.example.libraryapp.OLDdomain.payment.dto;

import com.example.libraryapp.OLDdomain.payment.PaymentDescription;
import com.example.libraryapp.OLDdomain.payment.PaymentMethod;
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
