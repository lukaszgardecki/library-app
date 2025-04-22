package com.example.authservice.common.exception;


import com.example.authservice.common.MessageKey;

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
