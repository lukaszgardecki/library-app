package com.example.loanservice.domain.ports.out;

import com.example.loanservice.domain.model.values.UserId;

public interface SourceValidatorPort {

    void validateUserIsOwner(UserId userId);
}
