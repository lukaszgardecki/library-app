package com.example.authservice.domain.exception;

import com.example.authservice.domain.MessageKey;

public class EmailAlreadyExistsException extends LibraryAppException {

    public EmailAlreadyExistsException() {
        super(MessageKey.VALIDATION_EMAIL_UNIQUE);
    }
}
