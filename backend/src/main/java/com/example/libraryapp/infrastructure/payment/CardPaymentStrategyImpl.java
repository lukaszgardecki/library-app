package com.example.libraryapp.infrastructure.payment;

import com.example.libraryapp.domain.payment.request.CardPaymentRequest;
import com.example.libraryapp.domain.payment.ports.PaymentStrategyPort;

public class CardPaymentStrategyImpl implements PaymentStrategyPort<CardPaymentRequest> {

    @Override
    public boolean processPayment(CardPaymentRequest request) {
        System.out.println("Zapłącono kartą.");
        return true;
    }
}
