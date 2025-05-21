package com.example.loanservice.domain.i18n;

public enum MessageKey {

    // MAIN
    ACCESS_DENIED("access-denied"),
    FORBIDDEN("forbidden"),
    BODY_MISSING("body-missing"),

    // BOOK ITEM LOAN
    LOAN_NOT_FOUND("loan.not-found"),
    LOAN_NOT_FOUND_ID("loan.not-found.id"),
    LOAN_RENEWAL_FAILED_RETURN_DATE("loan.renewal-failed.return-date");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
