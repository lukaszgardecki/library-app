package com.example.libraryapp.domain.user.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppException;

public class EmailAlreadyExistsException extends LibraryAppException {

    public EmailAlreadyExistsException() {
        super(MessageKey.VALIDATION_EMAIL_UNIQUE);
    }
}
