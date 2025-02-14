package com.example.libraryapp.domain.person.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppNotFoundException;

public class PersonNotFoundException extends LibraryAppNotFoundException {

    public PersonNotFoundException(Long personId) {
        super(MessageKey.PERSON_NOT_FOUND_ID, personId.toString());
    }
}
