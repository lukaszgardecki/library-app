package com.example.libraryapp.application.librarycard;

import com.example.libraryapp.domain.librarycard.model.LibraryCard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    LibraryCard execute(Long id) {
        return libraryCardService.getLibraryCardById(id);
    }
}
