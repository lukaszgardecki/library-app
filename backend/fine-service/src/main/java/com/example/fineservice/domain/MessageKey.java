package com.example.fineservice.domain;

public enum MessageKey {

    // USER
    USER_UNSETTLED_CHARGES("user.unsettled-charges"),

    // FINE
    FINE_NOT_FOUND_ID("fine.not-found.id"),
    FINE_ALREADY_PAID("fine.already-paid");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
