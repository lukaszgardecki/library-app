package com.example.userservice.domain.ports;

import com.example.userservice.domain.model.user.UserId;

public interface FineServicePort {

    void validateUserForFines(UserId userId);
}
