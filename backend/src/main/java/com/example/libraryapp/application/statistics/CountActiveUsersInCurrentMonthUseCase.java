package com.example.libraryapp.application.statistics;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountActiveUsersInCurrentMonthUseCase {
    private final StatisticsService statisticsService;

    long execute() {
        return statisticsService.countUniqueBorrowersInCurrentMonth();
    }
}
