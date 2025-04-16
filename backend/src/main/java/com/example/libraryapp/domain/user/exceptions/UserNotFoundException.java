package com.example.libraryapp.domain.user.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;
import com.example.libraryapp.domain.user.model.UserId;

public class UserNotFoundException extends LibraryAppNotFoundException {

    public UserNotFoundException() {
        super(MessageKey.USER_NOT_FOUND, "null");
    }

    public UserNotFoundException(UserId userId) {
        super(MessageKey.USER_NOT_FOUND_ID, userId.value().toString());
    }
}
