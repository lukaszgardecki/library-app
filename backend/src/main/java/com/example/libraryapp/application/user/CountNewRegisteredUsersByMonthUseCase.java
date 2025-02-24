package com.example.libraryapp.application.user;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountNewRegisteredUsersByMonthUseCase {
    private final UserService userService;

    long execute(int month, int year) {
        return userService.countNewRegisteredUsersByMonth(month, year);
    }
}
