package com.example.libraryapp.core.payment;


import com.example.libraryapp.domain.payment.ports.PaymentStrategyPort;
import com.example.libraryapp.domain.payment.request.CardPaymentRequest;
import com.example.libraryapp.domain.payment.request.CashPaymentRequest;
import com.example.libraryapp.domain.payment.request.PaymentRequest;
import com.example.libraryapp.infrastructure.payment.CardPaymentStrategyAdapter;
import com.example.libraryapp.infrastructure.payment.CashPaymentStrategyAdapter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@RequiredArgsConstructor
class PaymentPortFactory {
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
