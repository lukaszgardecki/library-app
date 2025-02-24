package com.example.libraryapp.application.statistics;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class CountTodayLoansUseCase {
    private final StatisticsService statisticsService;

    long execute() {
        return statisticsService.countBookItemLoansByDay(LocalDateTime.now());
    }
}
