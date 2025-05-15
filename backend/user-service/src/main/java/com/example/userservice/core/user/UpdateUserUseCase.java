package com.example.userservice.core.user;

import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.UserUpdate;
import com.example.userservice.domain.model.user.UserUpdateAdmin;
import com.example.userservice.domain.model.user.values.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserUseCase {
    private final UserService userService;

    User execute(UserId userId, UserUpdate userData) {
        return userService.updateUser(userId, userData);
    }

    User execute(UserId userId, UserUpdateAdmin userData) {
        return userService.updateUserByAdmin(userId, userData);
    }
}
