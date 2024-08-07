package com.example.libraryapp.domain.exception.rack;

import com.example.libraryapp.management.Message;

public class RackNotFoundException extends RuntimeException {

    public RackNotFoundException(Long id) {
        super(Message.RACK_NOT_FOUND.getMessage(id));
    }

}
