package com.example.userservice.domain.exception;

import com.example.userservice.domain.MessageKey;
import com.example.userservice.domain.model.person.PersonId;

public class PersonNotFoundException extends LibraryAppNotFoundException {

    public PersonNotFoundException(PersonId personId) {
        super(MessageKey.PERSON_NOT_FOUND_ID, personId.value().toString());
    }
}
