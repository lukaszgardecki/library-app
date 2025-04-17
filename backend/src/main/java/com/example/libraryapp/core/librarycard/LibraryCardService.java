package com.example.libraryapp.core.librarycard;

import com.example.libraryapp.domain.Constants;
import com.example.libraryapp.domain.librarycard.exceptions.LibraryCardNotFoundException;
import com.example.libraryapp.domain.librarycard.model.*;
import com.example.libraryapp.domain.librarycard.ports.LibraryCardRepositoryPort;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class LibraryCardService {
    private final LibraryCardRepositoryPort libraryCardRepository;

    LibraryCardId createLibraryCard(UserId userId) {
        LibraryCard libraryCard = createCard(userId);
        LibraryCard newCard = libraryCardRepository.save(libraryCard);
        String newCardNum = generateNum(newCard.getId());
        newCard.setBarcode(new LibraryCardBarcode(newCardNum));
        LibraryCard savedCard = libraryCardRepository.save(newCard);
        return savedCard.getId();
    }

    LibraryCard getLibraryCardById(LibraryCardId id) {
        return libraryCardRepository.findById(id)
                .orElseThrow(() -> new LibraryCardNotFoundException(id));
    }

    LibraryCard save(LibraryCard card) {
        return libraryCardRepository.save(card);
    }

    void changeLibraryCardStatusTo(LibraryCardStatus status, LibraryCardId cardId) {
        libraryCardRepository.changeStatusById(status, cardId);
    }

    private LibraryCard createCard(UserId userId) {
        return LibraryCard.builder()
                .issuedAt(new IssuedDate(LocalDateTime.now()))
                .status(LibraryCardStatus.ACTIVE)
                .userId(userId)
                .build();
    }

    private String generateNum(LibraryCardId cardId) {
        int cardIdLength = String.valueOf(cardId.value()).length();
        int leadZeros = Constants.CARD_NUM_LENGTH - cardIdLength;
        return Constants.LIBRARY_NUM + Constants.CARD_START_CODE + "0".repeat(leadZeros) + cardId.value();
    }
}
