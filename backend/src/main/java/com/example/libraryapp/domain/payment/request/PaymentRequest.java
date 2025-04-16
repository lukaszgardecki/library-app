package com.example.libraryapp.domain.payment.request;

import com.example.libraryapp.domain.payment.model.PaymentAmount;
import com.example.libraryapp.domain.payment.model.PaymentDescription;
import com.example.libraryapp.domain.payment.model.PaymentMethod;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public abstract class PaymentRequest {
    private PaymentAmount amount;
    private UserId userId;
    private PaymentDescription description;
    private PaymentMethod method;
}
