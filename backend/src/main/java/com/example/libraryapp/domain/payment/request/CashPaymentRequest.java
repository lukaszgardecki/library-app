package com.example.libraryapp.domain.payment.request;

import com.example.libraryapp.domain.payment.model.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CashPaymentRequest extends PaymentRequest {

    public CashPaymentRequest(
            BigDecimal amount, Long userId, String description, PaymentMethod method
    ) {
        super(amount, userId, description, method);
    }
}
