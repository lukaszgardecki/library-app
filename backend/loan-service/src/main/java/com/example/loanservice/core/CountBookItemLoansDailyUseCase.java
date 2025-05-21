package com.example.loanservice.core;

import com.example.loanservice.domain.model.values.LoanStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
class CountBookItemLoansDailyUseCase {
    private final BookItemLoanService bookItemLoanService;

    List<Object[]> execute(LocalDate startDate, LocalDate endDate, LoanStatus status) {
        return bookItemLoanService.countBookItemLoansDaily(startDate, endDate, status);
    }
}
