package com.example.userservice.domain.exception;

import com.example.userservice.domain.i18n.MessageKey;
import com.example.userservice.domain.model.person.values.PersonId;

public class PersonNotFoundException extends LibraryAppNotFoundException {

    public PersonNotFoundException(PersonId personId) {
        super(MessageKey.PERSON_NOT_FOUND_ID, personId.value().toString());
    }
}
