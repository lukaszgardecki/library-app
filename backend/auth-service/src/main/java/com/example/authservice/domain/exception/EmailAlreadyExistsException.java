package com.example.authservice.domain.exception;

import com.example.authservice.domain.i18n.MessageKey;

public class EmailAlreadyExistsException extends LibraryAppException {

    public EmailAlreadyExistsException() {
        super(MessageKey.VALIDATION_EMAIL_UNIQUE);
    }
}
