package com.example.libraryapp.domain.payment.strategies;

import java.math.BigDecimal;

public interface PayStrategy {
    void pay(BigDecimal paymentAmount);
}
