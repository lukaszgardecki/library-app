package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWdomain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetUserUseCase {
    private final UserService userService;

    User execute(Long id) {
        return userService.getUser(id);
    }

    User execute(String email) {
        return userService.getUser(email);
    }
}
