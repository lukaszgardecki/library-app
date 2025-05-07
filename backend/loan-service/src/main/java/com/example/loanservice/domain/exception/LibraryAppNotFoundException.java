package com.example.loanservice.domain.exception;

import com.example.loanservice.domain.i18n.MessageKey;
import lombok.Getter;

@Getter
public class LibraryAppNotFoundException extends LibraryAppException {
    private final String sourceId;

    protected LibraryAppNotFoundException(MessageKey messageKey, String sourceId) {
        super(messageKey);
        this.sourceId = sourceId;
    }
}
