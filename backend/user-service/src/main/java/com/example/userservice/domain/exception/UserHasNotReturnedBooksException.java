package com.example.userservice.domain.exception;


import com.example.userservice.domain.MessageKey;
import com.example.userservice.domain.exception.LibraryAppException;

public class UserHasNotReturnedBooksException extends LibraryAppException {
    public UserHasNotReturnedBooksException() {
        super(MessageKey.USER_NOT_RETURNED_BOOKS);
    }
}
