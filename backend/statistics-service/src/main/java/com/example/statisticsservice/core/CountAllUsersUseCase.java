package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.ports.out.BorrowerRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountAllUsersUseCase {
    private final BorrowerRepositoryPort borrowerRepository;

    long execute() {
        return borrowerRepository.count();
    }
}
