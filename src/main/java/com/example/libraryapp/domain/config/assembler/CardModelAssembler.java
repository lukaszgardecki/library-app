package com.example.libraryapp.domain.config.assembler;

import com.example.libraryapp.domain.card.CardMapper;
import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.card.dto.CardDto;
import com.example.libraryapp.web.servicedesk.CardController;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.lang.NonNull;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Configuration
public class CardModelAssembler extends RepresentationModelAssemblerSupport<LibraryCard, CardDto> {

    public CardModelAssembler() {
        super(CardController.class, CardDto.class);
    }

    @Override
    @NonNull
    public CardDto toModel(@NonNull LibraryCard card) {
        CardDto cardDto = CardMapper.map(card);
        cardDto.add(linkTo(methodOn(CardController.class).getSingleCard(card.getId())).withSelfRel());
        cardDto.add(linkTo(methodOn(CardController.class).getAllCards(null)).withRel(IanaLinkRelations.COLLECTION));
        return cardDto;
    }

    @Override
    @NonNull
    public CollectionModel<CardDto> toCollectionModel(@NonNull Iterable<? extends LibraryCard> entities) {
        CollectionModel<CardDto> collectionModel = super.toCollectionModel(entities);
        collectionModel.add(linkTo(methodOn(CardController.class).getAllCards(null)).withSelfRel());
        return collectionModel;
    }
}
