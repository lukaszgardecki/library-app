package com.example.warehouseservice.domain.exception;

import com.example.warehouseservice.domain.MessageKey;
import com.example.warehouseservice.domain.exception.LibraryAppException;

public class ShelfException extends LibraryAppException {

    public ShelfException(MessageKey messageKey) {
        super(messageKey);
    }
}
