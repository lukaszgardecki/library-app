package com.example.libraryapp.domain.user.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class UserNotFoundException extends LibraryAppNotFoundException {

    public UserNotFoundException() {
        super(MessageKey.USER_NOT_FOUND, "null");
    }

    public UserNotFoundException(Long userId) {
        super(MessageKey.USER_NOT_FOUND_ID, userId.toString());
    }
}
