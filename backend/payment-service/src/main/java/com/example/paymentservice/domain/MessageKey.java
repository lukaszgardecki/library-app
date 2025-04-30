package com.example.paymentservice.domain;

public enum MessageKey {

    // PAYMENT
    PAYMENT_NOT_FOUND_ID("payment.not-found.id");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
