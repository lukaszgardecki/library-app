package com.example.loanservice.core;

import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.LoanId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemLoanUseCase {
    private final BookItemLoanService bookItemLoanService;

    BookItemLoan execute(LoanId id) {
        return bookItemLoanService.getBookItemLoanById(id);
    }
}
