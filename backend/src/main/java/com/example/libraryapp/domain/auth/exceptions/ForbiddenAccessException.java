package com.example.libraryapp.domain.auth.exceptions;


import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppException;

public class ForbiddenAccessException extends LibraryAppException {

    public ForbiddenAccessException() {
        super(MessageKey.FORBIDDEN);
    }
}
