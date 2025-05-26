package com.example.userservice.domain.i18n;

public enum MessageKey {

    // MAIN
    ACCESS_DENIED("access-denied"),
    FORBIDDEN("forbidden"),
    BODY_MISSING("body-missing"),

    // PERSON
    PERSON_NOT_FOUND_ID("person.not-found.id"),

    // USER
    USER_NOT_FOUND("user.not-found"),
    USER_NOT_FOUND_ID("user.not-found.id"),
    USER_NOT_FOUND_PERSON_ID("user.not-found.person.id"),
    USER_NOT_RETURNED_BOOKS("user.not-returned-books"),
    USER_UNSETTLED_CHARGES("user.unsettled-charges"),

    // BOOK ITEM LOAN
    LOAN_LIMIT_EXCEEDED("loan.limit-exceeded"),

    // BOOK ITEM REQUEST
    REQUEST_LIMIT_EXCEEDED("request.limit-exceeded"),

    // CARD
    CARD_NOT_FOUND_ID("card.not-found.id");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
