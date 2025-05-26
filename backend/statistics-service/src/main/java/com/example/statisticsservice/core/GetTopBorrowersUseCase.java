package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.model.borrower.Borrower;
import com.example.statisticsservice.domain.model.borrower.values.LoansCount;
import com.example.statisticsservice.domain.ports.out.BorrowerRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
class GetTopBorrowersUseCase {
    private final BorrowerRepositoryPort borrowerRepository;

    List<Borrower> execute(int limit) {
        return borrowerRepository.getBorrowers(limit).stream()
                .sorted(Comparator.comparing(Borrower::getLoans, Comparator.comparing(LoansCount::value).reversed())
                        .thenComparing(b -> b.getLastName().value())
                ).toList();
    }
}
