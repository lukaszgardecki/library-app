package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.model.UserId;

public interface SourceValidator {

    void validateUserIsOwner(UserId userId);
}
