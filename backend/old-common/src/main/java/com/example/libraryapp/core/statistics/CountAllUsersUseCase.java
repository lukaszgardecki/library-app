package com.example.libraryapp.core.statistics;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountAllUsersUseCase {
    private final StatisticsService statisticsService;

    long execute() {
        return statisticsService.countAllUsers();
    }
}
