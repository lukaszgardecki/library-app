package com.example.libraryapp.domain.card;

import com.example.libraryapp.domain.card.dto.CardDto;

public class CardMapper {

    public static CardDto map(LibraryCard entity) {
        return CardDto.builder()
                .id(entity.getId())
                .barcode(entity.getBarcode())
                .issuedAt(entity.getIssuedAt())
                .active(entity.isActive())
                .build();
    }
}
