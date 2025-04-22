package com.example.catalogservice.bookitem.domain.exceptions;

import com.example.catalogservice.common.MessageKey;
import com.example.catalogservice.common.exception.LibraryAppException;

public class BookItemException extends LibraryAppException {

    public BookItemException(MessageKey messageKey) {
        super(messageKey);
    }
}
