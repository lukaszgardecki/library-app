package com.example.paymentservice.domain.request;

import com.example.paymentservice.domain.model.PaymentAmount;
import com.example.paymentservice.domain.model.PaymentDescription;
import com.example.paymentservice.domain.model.PaymentMethod;
import com.example.paymentservice.domain.model.UserId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CashPaymentRequest extends PaymentRequest {

    public CashPaymentRequest(
            PaymentAmount amount, UserId userId, PaymentDescription description, PaymentMethod method
    ) {
        super(amount, userId, description, method);
    }
}
