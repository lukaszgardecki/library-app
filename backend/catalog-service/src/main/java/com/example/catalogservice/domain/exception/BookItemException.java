package com.example.catalogservice.domain.exception;

import com.example.catalogservice.domain.i18n.MessageKey;

public class BookItemException extends LibraryAppException {

    public BookItemException(MessageKey messageKey) {
        super(messageKey);
    }
}
