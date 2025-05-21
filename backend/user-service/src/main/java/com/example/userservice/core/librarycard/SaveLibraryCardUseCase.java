package com.example.userservice.core.librarycard;

import com.example.userservice.domain.model.librarycard.LibraryCard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SaveLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    LibraryCard execute(LibraryCard card) {
        return libraryCardService.save(card);
    }
}
