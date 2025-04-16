package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.model.Email;
import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserUseCase {
    private final UserService userService;

    User execute(UserId id) {
        return userService.getUserById(id);
    }

    User execute(Email email) {
        return userService.getUserByEmail(email);
    }
}
