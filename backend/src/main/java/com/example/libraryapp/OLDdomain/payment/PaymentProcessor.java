package com.example.libraryapp.OLDdomain.payment;

import com.example.libraryapp.OLDdomain.payment.dto.PaymentRequest;
import com.example.libraryapp.OLDdomain.payment.strategies.PayByCreditCard;
import com.example.libraryapp.OLDdomain.payment.strategies.PayInCash;
import com.example.libraryapp.OLDdomain.payment.strategies.PayStrategy;

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
