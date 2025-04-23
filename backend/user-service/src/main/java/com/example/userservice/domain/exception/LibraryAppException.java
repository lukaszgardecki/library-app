package com.example.userservice.domain.exception;


import com.example.userservice.domain.MessageKey;

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
