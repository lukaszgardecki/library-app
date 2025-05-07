package com.example.authservice.domain.i18n;

public enum MessageKey {

    // MAIN
    ACCESS_DENIED("access-denied"),
    FORBIDDEN("forbidden"),
    BODY_MISSING("body-missing"),

    // VALIDATION
    VALIDATION_EMAIL_UNIQUE("validation.email.unique"),
    VALIDATION_BAD_CREDENTIALS("validation.bad-credentials"),

    // USER AUTH
    AUTH_DETAILS_NOT_FOUND_ID("auth-details.not-found.id"),
    AUTH_DETAILS_NOT_FOUND_USER_ID("auth-details.not-found.user-id"),
    AUTH_DETAILS_NOT_FOUND_USERNAME("auth-details.not-found.username"),

    // TOKEN
    TOKEN_NOT_FOUND_HASH("token.not-found.hash");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
