package com.example.authservice.auth.domain.exceptions;

import com.example.authservice.common.MessageKey;
import com.example.authservice.common.exception.LibraryAppException;

public class EmailAlreadyExistsException extends LibraryAppException {

    public EmailAlreadyExistsException() {
        super(MessageKey.VALIDATION_EMAIL_UNIQUE);
    }
}
