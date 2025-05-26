package com.example.userservice.domain.exception;


import com.example.userservice.domain.i18n.MessageKey;

public class UserException extends LibraryAppException {
    public UserException(MessageKey messageKey) {
        super(messageKey);
    }
}
