package com.example.libraryapp.domain.payment.exceptions;


public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException() {
    }

    public PaymentNotFoundException(Long paymentId) {
        super("Message.PAYMENT_NOT_FOUND.getMessage(paymentId)");
    }
}
