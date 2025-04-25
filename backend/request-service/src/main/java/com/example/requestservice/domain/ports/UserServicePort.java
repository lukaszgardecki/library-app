package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.model.UserId;

public interface UserServicePort {
    void verifyUserForBookItemRequest(UserId userId);
}
