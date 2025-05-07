package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.model.user.values.UserId;

public interface FineServicePort {

    void validateUserForFines(UserId userId);
}
