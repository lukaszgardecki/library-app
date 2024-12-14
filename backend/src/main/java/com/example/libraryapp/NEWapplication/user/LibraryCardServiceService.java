package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWapplication.constants.Constants;
import com.example.libraryapp.NEWdomain.user.model.CardStatus;
import com.example.libraryapp.NEWdomain.user.model.LibraryCard;
import com.example.libraryapp.NEWdomain.user.ports.LibraryCardRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class LibraryCardServiceService {
    private final LibraryCardRepository libraryCardRepository;

    Long createLibraryCard() {
        LibraryCard libraryCard = createCard();
        LibraryCard newCard = libraryCardRepository.save(libraryCard);
        String newCardNum = generateNum(newCard.getId());
        newCard.setBarcode(newCardNum);
        LibraryCard savedCard = libraryCardRepository.save(newCard);
        return savedCard.getId();
    }

    private LibraryCard createCard() {
        return LibraryCard.builder()
                .issuedAt(LocalDateTime.now())
                .status(CardStatus.ACTIVE)
                .build();
    }

    private String generateNum(Long cardId) {
        int cardIdLength = String.valueOf(cardId).length();
        int leadZeros = Constants.CARD_NUM_LENGTH - cardIdLength;
        return Constants.LIBRARY_NUM + Constants.CARD_START_CODE + "0".repeat(leadZeros) + cardId;
    }
}
