package com.example.warehouseservice.domain.exception;

import com.example.warehouseservice.domain.i18n.MessageKey;

public class RackException extends LibraryAppException {

    public RackException(MessageKey messageKey) {
        super(messageKey);
    }
}
