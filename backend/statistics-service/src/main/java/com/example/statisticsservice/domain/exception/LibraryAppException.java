package com.example.statisticsservice.domain.exception;

import com.example.statisticsservice.domain.i18n.MessageKey;

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
