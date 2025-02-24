package com.example.libraryapp.domain.payment.request;

import com.example.libraryapp.domain.payment.model.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CardPaymentRequest extends PaymentRequest {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate; // Format: MM/YY
    private String cvv;

    public CardPaymentRequest(
            BigDecimal amount, Long userId, String description, PaymentMethod method,
            String cardNumber, String cardHolderName, String expirationDate, String cvv
    ) {
        super(amount, userId, description, method);
        this.cardNumber = cardNumber;
        this.cardHolderName = cardHolderName;
        this.expirationDate = expirationDate;
        this.cvv = cvv;
    }
}
