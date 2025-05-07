package com.example.paymentservice.domain.ports.out;

import com.example.paymentservice.domain.model.values.UserId;

public interface SourceValidatorPort {

    void validateUserIsOwner(UserId userId);
}
