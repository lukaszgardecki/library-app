package com.example.userservice.user.core;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountAllUseCase {
    private final UserService userService;

    long execute() {
        return userService.countAll();
    }
}
