package com.example.userservice.domain.exception;


import com.example.userservice.domain.MessageKey;
import com.example.userservice.domain.exception.LibraryAppException;

public class UserException extends LibraryAppException {
    public UserException(MessageKey messageKey) {
        super(messageKey);
    }
}
