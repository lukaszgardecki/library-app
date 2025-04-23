package com.example.userservice.domain.exception;

import com.example.userservice.domain.MessageKey;
import com.example.userservice.domain.model.user.UserId;

public class UserNotFoundException extends LibraryAppNotFoundException {

    public UserNotFoundException() {
        super(MessageKey.USER_NOT_FOUND, "null");
    }

    public UserNotFoundException(UserId userId) {
        super(MessageKey.USER_NOT_FOUND_ID, userId.value().toString());
    }
}
