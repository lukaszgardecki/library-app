package com.example.libraryapp.application.statistics;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountNewUsersInCurrentMonthUseCase {
    private final StatisticsService statisticsService;

    long execute() {
        return statisticsService.countNewRegisteredUsersInCurrentMonth();
    }
}
