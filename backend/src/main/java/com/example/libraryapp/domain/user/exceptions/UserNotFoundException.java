package com.example.libraryapp.domain.user.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
    }

    public UserNotFoundException(Long userId) {
        super("Message.MEMBER_NOT_FOUND.getMessage(userId)");
    }
}
