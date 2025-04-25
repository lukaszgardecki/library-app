package com.example.paymentservice.infrastructure.payment;

import com.example.paymentservice.domain.ports.PaymentStrategyPort;
import com.example.paymentservice.domain.request.CashPaymentRequest;

public class CashPaymentStrategyAdapter implements PaymentStrategyPort<CashPaymentRequest> {

    @Override
    public boolean processPayment(CashPaymentRequest request) {
        System.out.println("Zapłacono gotówką.");
        return true;
    }
}
