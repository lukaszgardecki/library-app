package com.example.libraryapp.domain.payment.model;

import com.example.libraryapp.domain.user.model.UserId;
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
public class Payment {
    private PaymentId id;
    private PaymentAmount amount;
    private PaymentCreationDate creationDate;
    private UserId userId;
    private PaymentDescription description;
    private PaymentMethod method;
    private PaymentStatus status;
}
