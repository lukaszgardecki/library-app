package com.example.fineservice.domain.exceptions;

import com.example.fineservice.domain.i18n.MessageKey;
import com.example.fineservice.domain.model.values.FineId;

public class FineNotFoundException extends LibraryAppNotFoundException {

    public FineNotFoundException(FineId fineId) {
        super(MessageKey.FINE_NOT_FOUND_ID, fineId.value().toString());
    }
}
