package com.example.libraryapp.application.fine;

import com.example.libraryapp.domain.fine.model.FineId;
import com.example.libraryapp.domain.fine.model.FineStatus;
import com.example.libraryapp.domain.fine.ports.FineRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CancelFineUseCase {
    private final FineRepositoryPort repository;

    void execute(FineId fineId) {
        repository.setStatus(fineId, FineStatus.CANCELED);
    }
}
