package com.example.warehouseservice.domain;

public enum MessageKey {

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
