package com.example.paymentservice.infrastructure.payment;

import com.example.paymentservice.domain.ports.out.PaymentStrategyPort;
import com.example.paymentservice.domain.request.CardPaymentRequest;
import org.springframework.stereotype.Component;

@Component
public class CardPaymentStrategyAdapter implements PaymentStrategyPort<CardPaymentRequest> {

    @Override
    public boolean processPayment(CardPaymentRequest request) {
        System.out.println("Zapłacono kartą.");
        return true;
    }

    @Override
    public Class<CardPaymentRequest> supportedType() {
        return CardPaymentRequest.class;
    }
}
