package com.example.libraryapp.application.librarycard;

import com.example.libraryapp.domain.Constants;
import com.example.libraryapp.domain.librarycard.exceptions.LibraryCardNotFoundException;
import com.example.libraryapp.domain.librarycard.model.LibraryCard;
import com.example.libraryapp.domain.librarycard.model.LibraryCardStatus;
import com.example.libraryapp.domain.librarycard.ports.LibraryCardRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class LibraryCardService {
    private final LibraryCardRepositoryPort libraryCardRepository;

    Long createLibraryCard(Long userId) {
        LibraryCard libraryCard = createCard(userId);
        LibraryCard newCard = libraryCardRepository.save(libraryCard);
        String newCardNum = generateNum(newCard.getId());
        newCard.setBarcode(newCardNum);
        LibraryCard savedCard = libraryCardRepository.save(newCard);
        return savedCard.getId();
    }

    LibraryCard getLibraryCardById(Long id) {
        return libraryCardRepository.findById(id)
                .orElseThrow(() -> new LibraryCardNotFoundException(id));
    }

    LibraryCard save(LibraryCard card) {
        return libraryCardRepository.save(card);
    }

    void changeLibraryCardStatusTo(LibraryCardStatus status, Long userId) {
        libraryCardRepository.changeStatusByUserId(status, userId);
    }

    private LibraryCard createCard(Long userId) {
        return LibraryCard.builder()
                .issuedAt(LocalDateTime.now())
                .status(LibraryCardStatus.ACTIVE)
                .userId(userId)
                .build();
    }

    private String generateNum(Long cardId) {
        int cardIdLength = String.valueOf(cardId).length();
        int leadZeros = Constants.CARD_NUM_LENGTH - cardIdLength;
        return Constants.LIBRARY_NUM + Constants.CARD_START_CODE + "0".repeat(leadZeros) + cardId;
    }
}
