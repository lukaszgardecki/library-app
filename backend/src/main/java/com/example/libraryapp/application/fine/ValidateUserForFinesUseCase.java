package com.example.libraryapp.application.fine;

import com.example.libraryapp.domain.fine.model.FineStatus;
import com.example.libraryapp.domain.user.exceptions.UnsettledFineException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ValidateUserForFinesUseCase {
    private final FineService fineService;

    void execute(Long userId) {
        fineService.getAllByUserId(userId).stream()
                .filter(fine -> fine.getStatus() == FineStatus.PENDING)
                .findAny()
                .ifPresent(fine -> {
                    throw new UnsettledFineException();
                });

    }
}
