package com.example.warehouseservice.domain.exception;

import com.example.warehouseservice.domain.i18n.MessageKey;

public class ShelfException extends LibraryAppException {

    public ShelfException(MessageKey messageKey) {
        super(messageKey);
    }
}
