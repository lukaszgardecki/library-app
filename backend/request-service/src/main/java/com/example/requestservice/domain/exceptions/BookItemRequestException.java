package com.example.requestservice.domain.exceptions;

import com.example.requestservice.domain.MessageKey;

public class BookItemRequestException extends LibraryAppException {
    public BookItemRequestException(MessageKey messageKey) {
        super(messageKey);
    }
}
