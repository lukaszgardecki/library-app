package com.example.activityservice.domain.exception;

import com.example.activityservice.domain.MessageKey;

public abstract class LibraryAppException extends RuntimeException {
    private final MessageKey messageKey;

    protected LibraryAppException(MessageKey messageKey) {
        super(messageKey.name());
        this.messageKey = messageKey;
    }

    public MessageKey getMessageKey() {
        return messageKey;
    }
}
