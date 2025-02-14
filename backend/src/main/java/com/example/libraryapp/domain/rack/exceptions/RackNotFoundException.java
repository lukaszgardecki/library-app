package com.example.libraryapp.domain.rack.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class RackNotFoundException extends LibraryAppNotFoundException {

    public RackNotFoundException(Long rackId) {
        super(MessageKey.RACK_NOT_FOUND_ID, rackId.toString());
    }

    public RackNotFoundException(String location) {
        super(MessageKey.RACK_NOT_FOUND_LOCATION, location);
    }
}
