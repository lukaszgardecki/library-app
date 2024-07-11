package com.example.libraryapp.domain.exception.auth;

import com.example.libraryapp.management.Message;

public class ForbiddenAccessException extends RuntimeException {

    public ForbiddenAccessException() {
        super(Message.FORBIDDEN.getMessage());
    }
}
