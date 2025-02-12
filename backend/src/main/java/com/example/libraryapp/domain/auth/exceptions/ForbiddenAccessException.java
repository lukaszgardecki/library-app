package com.example.libraryapp.domain.auth.exceptions;


public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException() {
        super("Message.FORBIDDEN.getMessage()");
    }
}
