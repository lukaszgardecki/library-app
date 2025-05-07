package com.example.userservice.core.librarycard;

import com.example.userservice.domain.model.librarycard.LibraryCard;
import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.domain.ports.out.SourceValidatorPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetLibraryCardUseCase {
    private final LibraryCardService libraryCardService;
    private final SourceValidatorPort sourceValidator;

    LibraryCard execute(LibraryCardId id) {
        LibraryCard card = libraryCardService.getLibraryCardById(id);
        sourceValidator.validateUserIsOwner(card.getUserId());
        return card;
    }
}
