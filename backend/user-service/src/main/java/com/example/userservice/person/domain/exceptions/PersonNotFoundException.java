package com.example.userservice.person.domain.exceptions;

import com.example.userservice.common.MessageKey;
import com.example.userservice.common.exception.LibraryAppNotFoundException;
import com.example.userservice.person.domain.model.PersonId;

public class PersonNotFoundException extends LibraryAppNotFoundException {

    public PersonNotFoundException(PersonId personId) {
        super(MessageKey.PERSON_NOT_FOUND_ID, personId.value().toString());
    }
}
