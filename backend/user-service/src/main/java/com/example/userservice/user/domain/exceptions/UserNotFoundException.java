package com.example.userservice.user.domain.exceptions;

import com.example.userservice.common.MessageKey;
import com.example.userservice.common.exception.LibraryAppNotFoundException;
import com.example.userservice.user.domain.model.user.UserId;

public class UserNotFoundException extends LibraryAppNotFoundException {

    public UserNotFoundException() {
        super(MessageKey.USER_NOT_FOUND, "null");
    }

    public UserNotFoundException(UserId userId) {
        super(MessageKey.USER_NOT_FOUND_ID, userId.value().toString());
    }
}
