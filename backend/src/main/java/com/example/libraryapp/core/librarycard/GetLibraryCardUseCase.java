package com.example.libraryapp.core.librarycard;

import com.example.libraryapp.domain.librarycard.model.LibraryCard;
import com.example.libraryapp.domain.librarycard.model.LibraryCardId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    LibraryCard execute(LibraryCardId id) {
        return libraryCardService.getLibraryCardById(id);
    }
}
