package com.example.libraryapp.domain.payment.request;

import com.example.libraryapp.domain.payment.model.PaymentAmount;
import com.example.libraryapp.domain.payment.model.PaymentDescription;
import com.example.libraryapp.domain.payment.model.PaymentMethod;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CashPaymentRequest extends PaymentRequest {

    public CashPaymentRequest(
            PaymentAmount amount, UserId userId, PaymentDescription description, PaymentMethod method
    ) {
        super(amount, userId, description, method);
    }
}
