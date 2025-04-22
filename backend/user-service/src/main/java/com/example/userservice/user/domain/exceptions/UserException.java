package com.example.userservice.user.domain.exceptions;


import com.example.userservice.common.MessageKey;
import com.example.userservice.common.exception.LibraryAppException;

public class UserException extends LibraryAppException {
    public UserException(MessageKey messageKey) {
        super(messageKey);
    }
}
