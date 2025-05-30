package com.example.userservice.core.librarycard;

import com.example.userservice.domain.constants.Constants;
import com.example.userservice.domain.exception.LibraryCardNotFoundException;
import com.example.userservice.domain.model.librarycard.LibraryCard;
import com.example.userservice.domain.model.librarycard.values.IssuedDate;
import com.example.userservice.domain.model.librarycard.values.LibraryCardBarcode;
import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.domain.model.librarycard.values.LibraryCardStatus;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.ports.out.LibraryCardRepositoryPort;
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
