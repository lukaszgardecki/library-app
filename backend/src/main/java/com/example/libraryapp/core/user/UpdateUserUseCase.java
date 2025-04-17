package com.example.libraryapp.core.user;

import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserUseCase {
    private final UserService userService;

    User execute(UserId userId, UserUpdateDto userData) {
        return userService.updateUser(userId, userData);
    }
}
