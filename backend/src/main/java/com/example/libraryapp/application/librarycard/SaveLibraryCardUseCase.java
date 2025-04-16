package com.example.libraryapp.application.librarycard;

import com.example.libraryapp.domain.librarycard.model.LibraryCard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SaveLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    LibraryCard execute(LibraryCard card) {
        return libraryCardService.save(card);
    }
}
