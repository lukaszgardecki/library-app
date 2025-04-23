package com.example.catalogservice.domain.exception;

import com.example.catalogservice.domain.MessageKey;
import com.example.catalogservice.domain.exception.LibraryAppException;

public class BookItemException extends LibraryAppException {

    public BookItemException(MessageKey messageKey) {
        super(messageKey);
    }
}
