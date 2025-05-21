package com.example.paymentservice.core;

import com.example.paymentservice.domain.model.Payment;
import com.example.paymentservice.domain.model.PaymentProcessRequest;
import com.example.paymentservice.domain.model.values.PaymentId;
import com.example.paymentservice.domain.model.values.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class PaymentFacade {
    private final ProcessPaymentUseCase processPaymentUseCase;
    private final GetPaymentUseCase getPaymentUseCase;
    private final GetAllUserPaymentsUseCase getAllUserPaymentsUseCase;

    public Page<Payment> getAllByUserId(UserId userId, Pageable pageable) {
        return getAllUserPaymentsUseCase.execute(userId, pageable);
    }

    public Payment getPayment(PaymentId id) {
        return getPaymentUseCase.execute(id);
    }

    public Payment processPayment(PaymentProcessRequest paymentRequest) {
        return processPaymentUseCase.execute(paymentRequest);
    }
}
