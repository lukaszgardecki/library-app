package com.example.userservice.user.domain.ports;

import com.example.userservice.user.domain.model.user.UserId;

public interface FineServicePort {

    void validateUserForFines(UserId userId);
}
