package com.example.userservice.user.core;

import com.example.userservice.user.domain.model.user.User;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserUseCase {
    private final UserService userService;

    User execute(UserId id) {
        return userService.getUserById(id);
    }
}
