package com.example.libraryapp.OLDdomain.exception.auth;

import com.example.libraryapp.OLDmanagement.Message;

public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException() {
        super("Message.FORBIDDEN.getMessage()");
    }
}
