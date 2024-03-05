package com.example.libraryapp.domain.payment;

import com.example.libraryapp.domain.payment.dto.PaymentResponse;

public class PaymentMapper {

    public static PaymentResponse map(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .memberId(payment.getMember().getId())
                .creationDate(payment.getCreationDate())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .description(payment.getDescription())
                .build();
    }
}
