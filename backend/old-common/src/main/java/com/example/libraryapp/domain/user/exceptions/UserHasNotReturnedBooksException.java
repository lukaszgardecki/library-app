package com.example.libraryapp.domain.user.exceptions;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.exception.LibraryAppException;

public class UserHasNotReturnedBooksException extends LibraryAppException {
    public UserHasNotReturnedBooksException() {
        super(MessageKey.USER_NOT_RETURNED_BOOKS);
    }
}
