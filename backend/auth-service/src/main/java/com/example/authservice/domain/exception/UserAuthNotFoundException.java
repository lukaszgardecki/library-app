package com.example.authservice.domain.exception;

import com.example.authservice.domain.i18n.MessageKey;
import com.example.authservice.domain.model.authdetails.values.AuthDetailsId;
import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.UserId;

public class UserAuthNotFoundException extends LibraryAppNotFoundException {

    public UserAuthNotFoundException(AuthDetailsId authId) {
        super(MessageKey.AUTH_DETAILS_NOT_FOUND_ID, authId.value().toString());
    }

    public UserAuthNotFoundException(UserId userId) {
        super(MessageKey.AUTH_DETAILS_NOT_FOUND_USER_ID, userId.value().toString());
    }

    public UserAuthNotFoundException(Email username) {
        super(MessageKey.AUTH_DETAILS_NOT_FOUND_USERNAME, username.value());
    }
}
