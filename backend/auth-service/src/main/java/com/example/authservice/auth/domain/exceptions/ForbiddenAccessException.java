package com.example.authservice.auth.domain.exceptions;


import com.example.authservice.common.MessageKey;
import com.example.authservice.common.exception.LibraryAppException;

public class ForbiddenAccessException extends LibraryAppException {

    public ForbiddenAccessException() {
        super(MessageKey.FORBIDDEN);
    }
}
