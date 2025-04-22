package com.example.libraryapp.domain.payment.ports;

import com.example.libraryapp.domain.payment.request.PaymentRequest;

public interface PaymentStrategyPort<T extends PaymentRequest> {
    boolean processPayment(T request);
}
