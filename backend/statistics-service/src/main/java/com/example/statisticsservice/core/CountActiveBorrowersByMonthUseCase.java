package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.ports.out.BorrowerRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountActiveBorrowersByMonthUseCase {
    private final BorrowerRepositoryPort borrowerRepository;

    long execute(int monthValue) {
        return borrowerRepository.findAllByLastLoanMonth(monthValue).size();
    }
}
