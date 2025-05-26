package com.example.fineservice.core;

import com.example.fineservice.domain.exceptions.UnsettledFineException;
import com.example.fineservice.domain.model.values.FineStatus;
import com.example.fineservice.domain.model.values.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class VerifyUserForFinesUseCase {
    private final FineService fineService;

    void execute(UserId userId) {
        fineService.getAllByUserId(userId).stream()
                .filter(fine -> fine.getStatus() == FineStatus.PENDING)
                .findAny()
                .ifPresent(fine -> {
                    throw new UnsettledFineException();
                });

    }
}
