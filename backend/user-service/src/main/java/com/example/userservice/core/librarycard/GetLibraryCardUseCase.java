package com.example.userservice.core.librarycard;

import com.example.userservice.domain.model.librarycard.LibraryCard;
import com.example.userservice.domain.model.librarycard.LibraryCardId;
import com.example.userservice.domain.ports.SourceValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetLibraryCardUseCase {
    private final LibraryCardService libraryCardService;
    private final SourceValidator sourceValidator;

    LibraryCard execute(LibraryCardId id) {
        LibraryCard card = libraryCardService.getLibraryCardById(id);
        sourceValidator.validateUserIsOwner(card.getUserId());
        return card;
    }
}
