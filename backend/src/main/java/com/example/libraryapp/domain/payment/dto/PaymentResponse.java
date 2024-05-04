package com.example.libraryapp.domain.payment.dto;

import com.example.libraryapp.domain.payment.PaymentMethod;
import com.example.libraryapp.domain.payment.PaymentStatus;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponse extends RepresentationModel<PaymentResponse> {
    private Long id;
    private BigDecimal amount;
    private Long memberId;
    private LocalDateTime creationDate;
    private PaymentStatus status;
    private PaymentMethod method;
    private String description;
}
