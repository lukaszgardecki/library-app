package com.example.loanservice.core;

import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.LoanId;
import com.example.loanservice.domain.ports.out.SourceValidatorPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemLoanUseCase {
    private final BookItemLoanService bookItemLoanService;
    private final SourceValidatorPort sourceValidator;

    BookItemLoan execute(LoanId id) {
        BookItemLoan loan = bookItemLoanService.getBookItemLoanById(id);
        sourceValidator.validateUserIsOwner(loan.getUserId());
        return loan;
    }
}
