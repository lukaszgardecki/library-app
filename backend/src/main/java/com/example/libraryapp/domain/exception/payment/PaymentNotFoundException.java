package com.example.libraryapp.domain.exception.payment;

import com.example.libraryapp.management.Message;

public class PaymentNotFoundException extends RuntimeException {

    public PaymentNotFoundException() {
    }

    public PaymentNotFoundException(Long paymentId) {
        super(String.format(Message.PAYMENT_NOT_FOUND, paymentId));
    }
}
