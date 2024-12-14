package com.example.libraryapp.NEWdomain.useractivity.exceptions;

public class UserActivityNotFoundException extends RuntimeException {

    public UserActivityNotFoundException(Long id) {
        super("Message.ACTION_NOT_FOUND_ID.getMessage(id)");
    }
}
