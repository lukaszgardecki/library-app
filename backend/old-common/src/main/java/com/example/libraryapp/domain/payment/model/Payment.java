package com.example.libraryapp.domain.payment.model;

import com.example.libraryapp.domain.user.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
