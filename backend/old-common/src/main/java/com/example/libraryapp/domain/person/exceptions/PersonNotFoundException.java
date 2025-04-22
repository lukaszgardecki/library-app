package com.example.libraryapp.domain.person.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;
import com.example.userservice.common.person.model.PersonId;

public class PersonNotFoundException extends LibraryAppNotFoundException {

    public PersonNotFoundException(PersonId personId) {
        super(MessageKey.PERSON_NOT_FOUND_ID, personId.value().toString());
    }
}
