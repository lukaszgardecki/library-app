package com.example.paymentservice.domain.exceptions;

import com.example.paymentservice.domain.MessageKey;
import lombok.Getter;

@Getter
public class LibraryAppNotFoundException extends LibraryAppException {
    private final String sourceId;

    protected LibraryAppNotFoundException(MessageKey messageKey, String sourceId) {
        super(messageKey);
        this.sourceId = sourceId;
    }
}
