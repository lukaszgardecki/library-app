package com.example.paymentservice.core;

import com.example.paymentservice.domain.ports.out.PaymentStrategyPort;
import com.example.paymentservice.domain.request.CardPaymentRequest;
import com.example.paymentservice.domain.request.CashPaymentRequest;
import com.example.paymentservice.domain.request.PaymentRequest;
import com.example.paymentservice.infrastructure.payment.CardPaymentStrategyAdapter;
import com.example.paymentservice.infrastructure.payment.CashPaymentStrategyAdapter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@RequiredArgsConstructor
class PaymentFactory {
    private static final Map<Class<? extends PaymentRequest>, Function<PaymentRequest, PaymentStrategyPort<? extends PaymentRequest>>> paymentPorts = new HashMap<>();

    static {
        paymentPorts.put(CardPaymentRequest.class, request -> new CardPaymentStrategyAdapter());
        paymentPorts.put(CashPaymentRequest.class, request -> new CashPaymentStrategyAdapter());
    }

    @SuppressWarnings("unchecked")
    public <T extends PaymentRequest> PaymentStrategyPort<T> getPaymentStrategy(T request) {
        Function<PaymentRequest, PaymentStrategyPort<? extends PaymentRequest>> strategyFunction = paymentPorts.get(request.getClass());
        if (Objects.isNull(strategyFunction)) {
            throw new IllegalArgumentException("Payment method not supported for request type: " + request.getClass().getSimpleName());
        }
        return (PaymentStrategyPort<T>) strategyFunction.apply(request);
    }
}
