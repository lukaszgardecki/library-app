package com.example.loanservice.core;

import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.UserId;
import com.example.loanservice.domain.ports.out.BookItemLoanRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
class GetUserCurrentLoansUseCase {
    private final BookItemLoanRepositoryPort repository;

    Page<BookItemLoan> execute(UserId userId, Pageable pageable) {
        return repository.findAllCurrentLoansByUserId(userId, pageable);
    }

    List<BookItemLoan> execute(UserId userId) {
        return repository.findAllCurrentLoansByUserId(userId);
    }
}
