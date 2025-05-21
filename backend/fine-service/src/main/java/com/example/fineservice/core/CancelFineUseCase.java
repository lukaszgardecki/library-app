package com.example.fineservice.core;

import com.example.fineservice.domain.model.values.FineId;
import com.example.fineservice.domain.model.values.FineStatus;
import com.example.fineservice.domain.ports.out.FineRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CancelFineUseCase {
    private final FineRepositoryPort repository;

    void execute(FineId fineId) {
        repository.setStatus(fineId, FineStatus.CANCELED);
    }
}
