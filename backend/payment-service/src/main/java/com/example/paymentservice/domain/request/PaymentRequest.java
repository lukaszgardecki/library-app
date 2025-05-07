package com.example.paymentservice.domain.request;

import com.example.paymentservice.domain.model.values.PaymentAmount;
import com.example.paymentservice.domain.model.values.PaymentDescription;
import com.example.paymentservice.domain.model.values.PaymentMethod;
import com.example.paymentservice.domain.model.values.UserId;
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
