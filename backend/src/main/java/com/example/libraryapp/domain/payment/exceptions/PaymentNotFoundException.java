package com.example.libraryapp.domain.payment.exceptions;


import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class PaymentNotFoundException extends LibraryAppNotFoundException {

    public PaymentNotFoundException(Long paymentId) {
        super(MessageKey.PAYMENT_NOT_FOUND_ID, paymentId.toString());
    }
}
