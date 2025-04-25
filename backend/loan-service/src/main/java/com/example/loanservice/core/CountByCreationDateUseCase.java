package com.example.loanservice.core;

import com.example.loanservice.domain.model.LoanCreationDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountByCreationDateUseCase {
    private final BookItemLoanService bookItemLoanService;

    long execute(LoanCreationDate day) {
        return bookItemLoanService.countByCreationDate(day);
    }
}
