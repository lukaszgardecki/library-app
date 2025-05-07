package com.example.requestservice.domain.exceptions;

import com.example.requestservice.domain.i18n.MessageKey;
import lombok.Getter;

@Getter
public class LibraryAppNotFoundException extends LibraryAppException {
    private final String sourceId;

    protected LibraryAppNotFoundException(MessageKey messageKey, String sourceId) {
        super(messageKey);
        this.sourceId = sourceId;
    }
}
