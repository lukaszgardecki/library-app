package com.example.paymentservice.core;

import com.example.paymentservice.domain.model.PaymentProcessRequest;
import com.example.paymentservice.domain.request.CardPaymentRequest;
import com.example.paymentservice.domain.request.CashPaymentRequest;
import com.example.paymentservice.domain.request.PaymentRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class PaymentRequestFactory {

    PaymentRequest create(PaymentProcessRequest request) {
        return switch (request.getMethod()) {
            case CASH -> createNewCashRequest(request);
            case CREDIT_CARD -> createNewCardRequest(request);
        };
    }

    private CashPaymentRequest createNewCashRequest(PaymentProcessRequest request) {
        return new CashPaymentRequest(
                request.getAmount(),
                request.getUserId(),
                request.getDescription(),
                request.getMethod()
        );
    }

    private CardPaymentRequest createNewCardRequest(PaymentProcessRequest request) {
        return new CardPaymentRequest(
                request.getAmount(),
                request.getUserId(),
                request.getDescription(),
                request.getMethod(),
                request.getCardPaymentDetails().getCardNumber(),
                request.getCardPaymentDetails().getCardHolderName(),
                request.getCardPaymentDetails().getExpirationDate(),
                request.getCardPaymentDetails().getCvv()
        );
    }
}
