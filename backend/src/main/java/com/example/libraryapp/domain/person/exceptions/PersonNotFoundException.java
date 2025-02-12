package com.example.libraryapp.domain.person.exceptions;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException() {
        super("brak persony");
    }
}
