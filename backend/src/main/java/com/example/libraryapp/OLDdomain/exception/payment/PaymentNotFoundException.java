package com.example.libraryapp.OLDdomain.exception.payment;

import com.example.libraryapp.OLDmanagement.Message;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException() {
    }

    public PaymentNotFoundException(Long paymentId) {
        super("Message.PAYMENT_NOT_FOUND.getMessage(paymentId)");
    }
}
