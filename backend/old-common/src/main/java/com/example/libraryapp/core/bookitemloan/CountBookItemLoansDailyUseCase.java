package com.example.libraryapp.core.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.LoanStatus;
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
