package com.example.userservice.core.user;

import com.example.userservice.domain.dto.user.UserUpdateDto;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserUseCase {
    private final UserService userService;

    User execute(UserId userId, UserUpdateDto userData) {
        return userService.updateUser(userId, userData);
    }
}
