package com.example.paymentservice.core;

import com.example.paymentservice.domain.ports.out.PaymentStrategyPort;
import com.example.paymentservice.domain.request.PaymentRequest;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class PaymentProcessor {
    private final List<PaymentStrategyPort<? extends PaymentRequest>> strategies;

    @SuppressWarnings("unchecked")
    public <T extends PaymentRequest> boolean pay(T request) {
        return strategies.stream()
                .filter(s -> s.supportedType().equals(request.getClass()))
                .findFirst()
                .map(s -> ((PaymentStrategyPort<T>) s).processPayment(request))
                .orElseThrow(() -> new IllegalArgumentException("No strategy for: " + request.getClass()));
    }
}
