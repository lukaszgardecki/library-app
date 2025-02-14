package com.example.libraryapp.domain.rack.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppException;

public class RackException extends LibraryAppException {

    public RackException(MessageKey messageKey) {
        super(messageKey);
    }
}
