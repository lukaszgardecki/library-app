package com.example.loanservice.core;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountUniqueBorrowersInCurrentMonthUseCase {
    private final BookItemLoanService bookItemLoanService;

    long execute() {
        return bookItemLoanService.countUniqueBorrowersInCurrentMonth();
    }
}
