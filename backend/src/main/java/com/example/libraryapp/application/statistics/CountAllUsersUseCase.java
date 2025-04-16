package com.example.libraryapp.application.statistics;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountAllUsersUseCase {
    private final StatisticsService statisticsService;

    long execute() {
        return statisticsService.countAllUsers();
    }
}
