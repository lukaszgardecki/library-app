package com.example.loanservice.core;

import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.UserId;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetAllUserLoansUseCase {
    private final BookItemLoanService bookItemLoanService;

    List<BookItemLoan> execute(UserId userId) {
        return bookItemLoanService.getAllBookItemLoans(userId);
    }
}
