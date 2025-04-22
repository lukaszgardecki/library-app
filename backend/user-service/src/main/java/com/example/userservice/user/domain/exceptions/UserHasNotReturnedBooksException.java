package com.example.userservice.user.domain.exceptions;


import com.example.userservice.common.MessageKey;
import com.example.userservice.common.exception.LibraryAppException;

public class UserHasNotReturnedBooksException extends LibraryAppException {
    public UserHasNotReturnedBooksException() {
        super(MessageKey.USER_NOT_RETURNED_BOOKS);
    }
}
