package com.example.paymentservice.domain.request;

import com.example.paymentservice.domain.model.PaymentAmount;
import com.example.paymentservice.domain.model.PaymentDescription;
import com.example.paymentservice.domain.model.PaymentMethod;
import com.example.paymentservice.domain.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class PaymentRequest {
    private PaymentAmount amount;
    private UserId userId;
    private PaymentDescription description;
    private PaymentMethod method;
}
