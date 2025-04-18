package com.example.libraryapp.domain.rack.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;
import com.example.libraryapp.domain.rack.model.RackId;

public class RackNotFoundException extends LibraryAppNotFoundException {

    public RackNotFoundException(RackId rackId) {
        super(MessageKey.RACK_NOT_FOUND_ID, rackId.value().toString());
    }

}
