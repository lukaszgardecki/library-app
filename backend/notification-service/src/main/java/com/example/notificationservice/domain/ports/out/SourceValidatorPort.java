package com.example.notificationservice.domain.ports.out;

import com.example.notificationservice.domain.model.values.UserId;

public interface SourceValidatorPort {

    void validateUserIsOwner(UserId userId);
}
