package com.example.paymentservice.domain.ports;

import com.example.paymentservice.domain.model.UserId;

public interface SourceValidator {

    void validateUserIsOwner(UserId userId);
}
