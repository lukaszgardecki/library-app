package com.example.libraryapp.application.librarycard;

import com.example.libraryapp.domain.librarycard.dto.LibraryCardDto;
import com.example.libraryapp.domain.librarycard.model.LibraryCard;

class LibraryCardMapper {

    static LibraryCard toModel(LibraryCardDto dto) {
        return LibraryCard.builder()
                .id(dto.getId())
                .barcode(dto.getBarcode())
                .issuedAt(dto.getIssuedAt())
                .status(dto.getStatus())
                .userId(dto.getUserId())
                .build();
    }

    static LibraryCardDto toDto(LibraryCard model) {
        return LibraryCardDto.builder()
                .id(model.getId())
                .barcode(model.getBarcode())
                .issuedAt(model.getIssuedAt())
                .status(model.getStatus())
                .userId(model.getUserId())
                .build();
    }
}
