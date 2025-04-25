package com.example.fineservice.domain.exceptions;

import com.example.fineservice.domain.MessageKey;
import com.example.fineservice.domain.model.FineId;

public class FineNotFoundException extends LibraryAppNotFoundException {

    public FineNotFoundException(FineId fineId) {
        super(MessageKey.FINE_NOT_FOUND_ID, fineId.value().toString());
    }
}
