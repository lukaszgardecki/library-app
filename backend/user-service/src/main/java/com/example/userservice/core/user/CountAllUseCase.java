package com.example.userservice.core.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountAllUseCase {
    private final UserService userService;

    long execute() {
        return userService.countAll();
    }
}
