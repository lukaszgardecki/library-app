package com.example.loanservice.core;

import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.UserId;
import com.example.loanservice.domain.ports.out.BookItemLoanRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetUserCurrentLoansUseCase {
    private final BookItemLoanRepositoryPort repository;

    List<BookItemLoan> execute(UserId userId) {
        return repository.findAllCurrentLoansByUserId(userId);
    }
}
