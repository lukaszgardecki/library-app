package com.example.libraryapp.domain.exception.rack;

public class RackException extends RuntimeException {

    public RackException(String message, String rackIdentifier) {
        super(String.format(message, rackIdentifier));
    }

}
