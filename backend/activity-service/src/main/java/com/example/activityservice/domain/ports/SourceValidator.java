package com.example.activityservice.domain.ports;

import com.example.activityservice.domain.model.UserId;

public interface SourceValidator {

    void validateUserIsOwner(UserId userId);
}
