package com.example.paymentservice.infrastructure.payment;

import com.example.paymentservice.domain.ports.out.PaymentStrategyPort;
import com.example.paymentservice.domain.request.CashPaymentRequest;
import org.springframework.stereotype.Component;

@Component
public class CashPaymentStrategyAdapter implements PaymentStrategyPort<CashPaymentRequest> {

    @Override
    public boolean processPayment(CashPaymentRequest request) {
        System.out.println("Zapłacono gotówką.");
        return true;
    }

    @Override
    public Class<CashPaymentRequest> supportedType() {
        return CashPaymentRequest.class;
    }
}
