package com.example.paymentservice.domain.request;

import com.example.paymentservice.domain.model.values.PaymentAmount;
import com.example.paymentservice.domain.model.values.PaymentDescription;
import com.example.paymentservice.domain.model.values.PaymentMethod;
import com.example.paymentservice.domain.model.values.UserId;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardPaymentRequest extends PaymentRequest {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate; // Format: MM/YY
    private String cvv;

    public CardPaymentRequest(
            PaymentAmount amount, UserId userId, PaymentDescription description, PaymentMethod method,
            String cardNumber, String cardHolderName, String expirationDate, String cvv
    ) {
        super(amount, userId, description, method);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }
}
