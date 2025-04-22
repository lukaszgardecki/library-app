package com.example.userservice.librarycard.core;

import com.example.userservice.librarycard.domain.model.LibraryCard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SaveLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    LibraryCard execute(LibraryCard card) {
        return libraryCardService.save(card);
    }
}
