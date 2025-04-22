package com.example.catalogservice.common.exception;

import com.example.catalogservice.common.MessageKey;
import lombok.Getter;

@Getter
public class LibraryAppNotFoundException extends LibraryAppException{
    private final String sourceId;

    protected LibraryAppNotFoundException(MessageKey messageKey, String sourceId) {
        super(messageKey);
        this.sourceId = sourceId;
    }
}
