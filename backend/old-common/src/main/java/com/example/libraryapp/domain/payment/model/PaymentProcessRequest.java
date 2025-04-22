package com.example.libraryapp.domain.payment.model;


import com.example.libraryapp.domain.user.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PaymentProcessRequest {
    private PaymentAmount amount;
    private UserId userId;
    private PaymentDescription description;
    private PaymentMethod method;
    private PaymentCardDetails cardPaymentDetails;
}
