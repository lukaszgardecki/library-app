package com.example.userservice.user.core;

import com.example.userservice.user.domain.dto.UserUpdateDto;
import com.example.userservice.user.domain.model.user.User;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserUseCase {
    private final UserService userService;

    User execute(UserId userId, UserUpdateDto userData) {
        return userService.updateUser(userId, userData);
    }
}
