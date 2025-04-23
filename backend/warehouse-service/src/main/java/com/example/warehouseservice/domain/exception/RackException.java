package com.example.warehouseservice.domain.exception;

import com.example.warehouseservice.domain.MessageKey;

public class RackException extends LibraryAppException {

    public RackException(MessageKey messageKey) {
        super(messageKey);
    }
}
