package com.example.libraryapp.application.librarycard;

import com.example.libraryapp.domain.librarycard.model.LibraryCardStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BlockLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    void execute(Long userId) {
        libraryCardService.changeLibraryCardStatusTo(LibraryCardStatus.INACTIVE, userId);
    }
}
