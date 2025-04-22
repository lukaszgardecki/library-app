package com.example.userservice.librarycard.core;

import com.example.userservice.librarycard.domain.model.LibraryCardId;
import com.example.userservice.librarycard.domain.model.LibraryCardStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ActivateLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    void execute(LibraryCardId cardId) {
        libraryCardService.changeLibraryCardStatusTo(LibraryCardStatus.ACTIVE, cardId);
    }
}
