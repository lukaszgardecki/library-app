package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.model.borrower.Borrower;
import com.example.statisticsservice.domain.ports.out.BorrowerRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetBorrowersUseCase {
    private final BorrowerRepositoryPort borrowerRepository;

    List<Borrower> execute(int limit) {
        return borrowerRepository.getBorrowers(limit);
    }
}
