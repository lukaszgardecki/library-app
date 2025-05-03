package com.example.requestservice.domain;

public enum MessageKey {

    // MAIN
    ACCESS_DENIED("access-denied"),
    FORBIDDEN("forbidden"),
    BODY_MISSING("body-missing"),

    // BOOK ITEM
    BOOK_ITEM_ALREADY_REQUESTED("book-item.already-requested"),

    // BOOK ITEM REQUEST
    REQUEST_NOT_FOUND("request.not-found"),
    REQUEST_NOT_FOUND_ID("request.not-found.id"),
    REQUEST_ALREADY_CREATED("request.already-created"),
    REQUEST_STATUS_NOT_READY("request.status.not-ready");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
