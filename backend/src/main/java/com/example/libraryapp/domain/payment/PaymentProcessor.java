package com.example.libraryapp.domain.payment;

import com.example.libraryapp.domain.payment.dto.PaymentRequest;
import com.example.libraryapp.domain.payment.strategies.PayByCreditCard;
import com.example.libraryapp.domain.payment.strategies.PayInCash;
import com.example.libraryapp.domain.payment.strategies.PayStrategy;

public class PaymentProcessor {

    public static PaymentStatus process(PaymentRequest payment) {
        PayStrategy strategy = switch (payment.getPaymentMethod()) {
            case CASH -> new PayInCash();
            case CREDIT_CARD -> new PayByCreditCard();
        };

        strategy.pay(payment.getAmount());
        return PaymentStatus.COMPLETED;
    }
}
