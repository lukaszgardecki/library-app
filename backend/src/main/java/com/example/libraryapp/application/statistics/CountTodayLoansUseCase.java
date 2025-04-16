package com.example.libraryapp.application.statistics;

import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class CountTodayLoansUseCase {
    private final StatisticsService statisticsService;

    long execute() {
        return statisticsService.countBookItemLoansByDay(new LoanCreationDate(LocalDateTime.now()));
    }
}
