package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserUseCase {
    private final UserService userService;

    User execute(Long id) {
        return userService.getUserById(id);
    }

    User execute(String email) {
        return userService.getUserByEmail(email);
    }
}
