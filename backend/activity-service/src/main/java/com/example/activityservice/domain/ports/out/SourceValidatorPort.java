package com.example.activityservice.domain.ports.out;

import com.example.activityservice.domain.model.values.UserId;

public interface SourceValidatorPort {

    void validateUserIsOwner(UserId userId);
}
