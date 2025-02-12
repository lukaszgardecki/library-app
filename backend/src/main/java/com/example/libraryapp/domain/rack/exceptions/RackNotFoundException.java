package com.example.libraryapp.domain.rack.exceptions;

public class RackNotFoundException extends RuntimeException {

    public RackNotFoundException(Long id) {
        super("Message.RACK_NOT_FOUND.getMessage(id)");
    }

    public RackNotFoundException(String location) {
        // TODO: 11.12.2024 zrobić message wyjątku dopasowany do lokalizacji
        super("Message.RACK_NOT_FOUND.getMessage(id)");
    }

}
