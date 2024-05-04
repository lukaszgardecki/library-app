package com.example.libraryapp.domain.card;

import com.example.libraryapp.domain.card.dto.CardDto;
import com.example.libraryapp.domain.config.assembler.CardModelAssembler;
import com.example.libraryapp.domain.exception.card.CardNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final LibraryCardRepository cardRepository;
    private final CardModelAssembler cardModelAssembler;
    private final PagedResourcesAssembler<LibraryCard> pagedResourcesAssembler;

    public PagedModel<CardDto> findAllCards(Pageable pageable) {
        Page<LibraryCard> cardsPage = cardRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(cardsPage, cardModelAssembler);
    }

    public CardDto findCardById(Long cardId) {
        LibraryCard card = findCard(cardId);
        return cardModelAssembler.toModel(card);
    }

    @Transactional
    public CardDto activateCard(Long cardId) {
        LibraryCard card = findCard(cardId);
        card.setActive(true);
        return cardModelAssembler.toModel(card);
    }

    @Transactional
    public CardDto blockCard(Long cardId) {
        LibraryCard card = findCard(cardId);
        card.setActive(false);
        return cardModelAssembler.toModel(card);
    }

    private LibraryCard findCard(Long id) {
        return cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFoundException(id));
    }
}
