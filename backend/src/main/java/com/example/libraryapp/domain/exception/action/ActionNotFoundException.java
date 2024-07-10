package com.example.libraryapp.domain.exception.action;

import com.example.libraryapp.management.Message;

public class ActionNotFoundException extends RuntimeException {

    public ActionNotFoundException(Long id) {
        super(Message.ACTION_NOT_FOUND_ID.getMessage(id));
    }
}
