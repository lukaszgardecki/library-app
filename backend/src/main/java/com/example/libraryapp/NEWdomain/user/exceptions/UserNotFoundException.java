package com.example.libraryapp.NEWdomain.user.exceptions;

import com.example.libraryapp.OLDmanagement.Message;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(Long userId) {
        super("Message.MEMBER_NOT_FOUND.getMessage(userId)");
    }
}
