package com.example.warehouseservice.domain;

public enum MessageKey {

    // MAIN
    ACCESS_DENIED("access-denied"),
    FORBIDDEN("forbidden"),
    BODY_MISSING("body-missing"),

    // RACK
    RACK_NOT_FOUND_ID("rack.not-found.id"),
    RACK_DELETION_FAILED("rack.deletion-failed"),

    // SHELF
    SHELF_NOT_FOUND_ID("shelf.not-found.id"),
    SHELF_DELETION_FAILED("shelf.deletion-failed");

    private final String key;

    MessageKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
