package com.example.requestservice.domain.ports.out;

import com.example.requestservice.domain.model.values.UserId;

public interface UserServicePort {
    void verifyUserForBookItemRequest(UserId userId);
}
