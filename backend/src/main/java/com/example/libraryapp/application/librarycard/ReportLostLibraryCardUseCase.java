package com.example.libraryapp.application.librarycard;

import com.example.libraryapp.domain.librarycard.model.LibraryCardId;
import com.example.libraryapp.domain.librarycard.model.LibraryCardStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ReportLostLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    void execute(LibraryCardId cardId) {
        libraryCardService.changeLibraryCardStatusTo(LibraryCardStatus.LOST, cardId);
    }
}
