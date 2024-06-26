package com.example.libraryapp.domain.exception.action;

import com.example.libraryapp.management.Message;

public class ActionNotFoundException extends RuntimeException {

    public ActionNotFoundException(Long id) {
        super(String.format(Message.ACTION_NOT_FOUND_BY_ID, id));
    }
}
