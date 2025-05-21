package com.example.userservice.domain.exception;


import com.example.userservice.domain.i18n.MessageKey;

public class UserHasNotReturnedBooksException extends LibraryAppException {
    public UserHasNotReturnedBooksException() {
        super(MessageKey.USER_NOT_RETURNED_BOOKS);
    }
}
