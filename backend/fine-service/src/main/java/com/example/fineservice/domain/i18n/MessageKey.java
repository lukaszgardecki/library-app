package com.example.fineservice.domain.i18n;

public enum MessageKey {

    // MAIN
    ACCESS_DENIED("access-denied"),
    FORBIDDEN("forbidden"),
    BODY_MISSING("body-missing"),

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
