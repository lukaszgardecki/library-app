package com.example.paymentservice.domain.request;

import com.example.paymentservice.domain.model.values.PaymentAmount;
import com.example.paymentservice.domain.model.values.PaymentDescription;
import com.example.paymentservice.domain.model.values.PaymentMethod;
import com.example.paymentservice.domain.model.values.UserId;
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
