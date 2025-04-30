package com.example.authservice.domain.exception;

import com.example.authservice.domain.MessageKey;
import com.example.authservice.domain.model.authdetails.Email;
import com.example.authservice.domain.model.authdetails.UserId;

public class UserAuthNotFoundException extends LibraryAppNotFoundException {

    public UserAuthNotFoundException() {
        super(MessageKey.AUTH_DETAILS_NOT_FOUND_ID, "");
    }

    public UserAuthNotFoundException(UserId userId) {
        super(MessageKey.AUTH_DETAILS_NOT_FOUND_USER_ID, userId.value().toString());
    }

    public UserAuthNotFoundException(Email username) {
        super(MessageKey.AUTH_DETAILS_NOT_FOUND_USERNAME, username.value());
    }
}
