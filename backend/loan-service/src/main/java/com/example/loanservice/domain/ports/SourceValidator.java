package com.example.loanservice.domain.ports;

import com.example.loanservice.domain.model.UserId;

public interface SourceValidator {

    void validateUserIsOwner(UserId userId);
}
