package com.example.paymentservice.domain.exceptions;

import com.example.paymentservice.domain.i18n.MessageKey;
import com.example.paymentservice.domain.model.values.PaymentId;

public class PaymentNotFoundException extends LibraryAppNotFoundException {

    public PaymentNotFoundException(PaymentId paymentId) {
        super(MessageKey.PAYMENT_NOT_FOUND_ID, paymentId.value().toString());
    }
}
