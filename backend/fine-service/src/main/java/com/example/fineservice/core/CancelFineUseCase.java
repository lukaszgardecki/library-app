package com.example.fineservice.core;

import com.example.fineservice.domain.model.FineId;
import com.example.fineservice.domain.model.FineStatus;
import com.example.fineservice.domain.ports.FineRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CancelFineUseCase {
    private final FineRepositoryPort repository;

    void execute(FineId fineId) {
        repository.setStatus(fineId, FineStatus.CANCELED);
    }
}
