package com.example.paymentservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentProcessRequest {
    private PaymentAmount amount;
    private UserId userId;
    private PaymentDescription description;
    private PaymentMethod method;
    private PaymentCardDetails cardPaymentDetails;
}
