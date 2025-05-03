package com.example.notificationservice.domain.ports;

import com.example.notificationservice.domain.model.UserId;

public interface SourceValidator {

    void validateUserIsOwner(UserId userId);
}
