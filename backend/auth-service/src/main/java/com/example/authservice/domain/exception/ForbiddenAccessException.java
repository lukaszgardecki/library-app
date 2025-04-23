package com.example.authservice.domain.exception;


import com.example.authservice.domain.MessageKey;

public class ForbiddenAccessException extends LibraryAppException {

    public ForbiddenAccessException() {
        super(MessageKey.FORBIDDEN);
    }
}
