package com.example.userservice.domain.exception;

import com.example.userservice.domain.i18n.MessageKey;
import com.example.userservice.domain.model.person.values.PersonId;
import com.example.userservice.domain.model.user.values.UserId;

public class UserNotFoundException extends LibraryAppNotFoundException {

    public UserNotFoundException() {
        super(MessageKey.USER_NOT_FOUND, "null");
    }

    public UserNotFoundException(UserId userId) {
        super(MessageKey.USER_NOT_FOUND_ID, userId.value().toString());
    }

    public UserNotFoundException(PersonId personId) {
        super(MessageKey.USER_NOT_FOUND_PERSON_ID, personId.value().toString());
    }
}
