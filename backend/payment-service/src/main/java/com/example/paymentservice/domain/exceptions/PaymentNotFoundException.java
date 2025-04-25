package com.example.paymentservice.domain.exceptions;

import com.example.paymentservice.domain.MessageKey;
import com.example.paymentservice.domain.model.PaymentId;

public class PaymentNotFoundException extends LibraryAppNotFoundException {

    public PaymentNotFoundException(PaymentId paymentId) {
        super(MessageKey.PAYMENT_NOT_FOUND_ID, paymentId.value().toString());
    }
}
