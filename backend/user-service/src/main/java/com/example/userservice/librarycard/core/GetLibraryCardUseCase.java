package com.example.userservice.librarycard.core;

import com.example.userservice.librarycard.domain.model.LibraryCard;
import com.example.userservice.librarycard.domain.model.LibraryCardId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    LibraryCard execute(LibraryCardId id) {
        return libraryCardService.getLibraryCardById(id);
    }
}
