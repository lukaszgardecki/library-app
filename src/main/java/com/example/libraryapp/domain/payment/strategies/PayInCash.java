package com.example.libraryapp.domain.payment.strategies;

import java.math.BigDecimal;

public class PayInCash implements PayStrategy {

    @Override
    public void pay(BigDecimal paymentAmount) {
        System.out.println("Zapłacono gotówką.");
    }
}
