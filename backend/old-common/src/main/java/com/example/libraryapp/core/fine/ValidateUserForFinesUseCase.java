package com.example.libraryapp.core.fine;

import com.example.libraryapp.domain.fine.model.FineStatus;
import com.example.libraryapp.domain.user.exceptions.UnsettledFineException;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ValidateUserForFinesUseCase {
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
