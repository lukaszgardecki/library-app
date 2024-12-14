package com.example.libraryapp.NEWdomain.auth.exceptions;


public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException() {
        super("Message.FORBIDDEN.getMessage()");
    }
}
