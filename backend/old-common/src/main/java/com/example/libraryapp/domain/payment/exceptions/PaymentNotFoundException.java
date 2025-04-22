package com.example.libraryapp.domain.payment.exceptions;


import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;
import com.example.libraryapp.domain.payment.model.PaymentId;

public class PaymentNotFoundException extends LibraryAppNotFoundException {

    public PaymentNotFoundException(PaymentId paymentId) {
        super(MessageKey.PAYMENT_NOT_FOUND_ID, paymentId.value().toString());
    }
}
