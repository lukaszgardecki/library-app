package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.dto.UserUpdateDto;
import com.example.libraryapp.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateUserUseCase {
    private final UserService userService;

    User execute(Long userId, UserUpdateDto userData) {
        return userService.updateUser(userId, userData);
    }
}
