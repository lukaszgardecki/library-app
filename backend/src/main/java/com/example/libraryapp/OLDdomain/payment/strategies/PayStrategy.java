package com.example.libraryapp.OLDdomain.payment.strategies;

import java.math.BigDecimal;

public interface PayStrategy {
    void pay(BigDecimal paymentAmount);
}
