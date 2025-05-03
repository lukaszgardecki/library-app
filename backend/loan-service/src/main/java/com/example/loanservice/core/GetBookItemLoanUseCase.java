package com.example.loanservice.core;

import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.LoanId;
import com.example.loanservice.domain.ports.SourceValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemLoanUseCase {
    private final BookItemLoanService bookItemLoanService;
    private final SourceValidator sourceValidator;

    BookItemLoan execute(LoanId id) {
        BookItemLoan loan = bookItemLoanService.getBookItemLoanById(id);
        sourceValidator.validateUserIsOwner(loan.getUserId());
        return loan;
    }
}
