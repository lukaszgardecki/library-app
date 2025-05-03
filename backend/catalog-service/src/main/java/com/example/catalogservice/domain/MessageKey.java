package com.example.catalogservice.domain;

public enum MessageKey {

    // MAIN
    ACCESS_DENIED("access-denied"),
    FORBIDDEN("forbidden"),
    BODY_MISSING("body-missing"),

    // BOOK
    BOOK_NOT_FOUND_ID("book.not-found.id"),

    // BOOK ITEM
    BOOK_ITEM_NOT_FOUND_ID("book-item.not-found.id"),
    BOOK_ITEM_LOST("book-item.lost"),
    BOOK_ITEM_DELETION_FAILED("book-item.deletion-failed");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
