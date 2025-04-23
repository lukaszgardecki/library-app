package com.example.userservice.core.librarycard;

import com.example.userservice.domain.model.librarycard.LibraryCardId;
import com.example.userservice.domain.model.librarycard.LibraryCardStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BlockLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    void execute(LibraryCardId cardId) {
        libraryCardService.changeLibraryCardStatusTo(LibraryCardStatus.INACTIVE, cardId);
    }
}
