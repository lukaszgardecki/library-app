package com.example.libraryapp.NEWapplication.fine;

import com.example.libraryapp.NEWdomain.user.exceptions.UnsettledFineException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ValidateUserForFinesUseCase {
    private final FineService fineService;

    void execute(Long userId) {
        fineService.getAllByUserId(userId).stream()
                .filter(fine -> !fine.getPaid())
                .findAny()
                .ifPresent(f -> {
                    throw new UnsettledFineException();
                });

    }
}
