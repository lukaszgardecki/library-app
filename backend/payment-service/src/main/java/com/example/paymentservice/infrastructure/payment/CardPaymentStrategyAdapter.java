package com.example.paymentservice.infrastructure.payment;

import com.example.paymentservice.domain.ports.out.PaymentStrategyPort;
import com.example.paymentservice.domain.request.CardPaymentRequest;

public class CardPaymentStrategyAdapter implements PaymentStrategyPort<CardPaymentRequest> {

    @Override
    public boolean processPayment(CardPaymentRequest request) {
        System.out.println("Zapłącono kartą.");
        return true;
    }
}
