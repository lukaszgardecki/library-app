package com.example.libraryapp.core.statistics;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountNewUsersInCurrentMonthUseCase {
    private final StatisticsService statisticsService;

    long execute() {
        return statisticsService.countNewRegisteredUsersInCurrentMonth();
    }
}
