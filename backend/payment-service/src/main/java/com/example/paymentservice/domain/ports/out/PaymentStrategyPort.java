package com.example.paymentservice.domain.ports.out;

import com.example.paymentservice.domain.request.PaymentRequest;

public interface PaymentStrategyPort<T extends PaymentRequest> {
    boolean processPayment(T request);
}
