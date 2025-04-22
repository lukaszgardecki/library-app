package com.example.libraryapp.infrastructure.payment;

import com.example.libraryapp.domain.payment.request.CashPaymentRequest;
import com.example.libraryapp.domain.payment.ports.PaymentStrategyPort;

public class CashPaymentStrategyAdapter implements PaymentStrategyPort<CashPaymentRequest> {

    @Override
    public boolean processPayment(CashPaymentRequest request) {
        System.out.println("Zapłacono gotówką.");
        return true;
    }
}
