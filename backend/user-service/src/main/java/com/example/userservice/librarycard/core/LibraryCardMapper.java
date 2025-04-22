package com.example.userservice.librarycard.core;

import com.example.userservice.librarycard.domain.dto.LibraryCardDto;
import com.example.userservice.librarycard.domain.model.IssuedDate;
import com.example.userservice.librarycard.domain.model.LibraryCard;
import com.example.userservice.librarycard.domain.model.LibraryCardBarcode;
import com.example.userservice.librarycard.domain.model.LibraryCardId;
import com.example.userservice.user.domain.model.user.UserId;

class LibraryCardMapper {

    static LibraryCard toModel(LibraryCardDto dto) {
        return LibraryCard.builder()
                .id(new LibraryCardId(dto.getId()))
                .barcode(new LibraryCardBarcode(dto.getBarcode()))
                .issuedAt(new IssuedDate(dto.getIssuedAt()))
                .status(dto.getStatus())
                .userId(new UserId(dto.getUserId()))
                .build();
    }

    static LibraryCardDto toDto(LibraryCard model) {
        return LibraryCardDto.builder()
                .id(model.getId().value())
                .barcode(model.getBarcode().value())
                .issuedAt(model.getIssuedAt().value())
                .status(model.getStatus())
                .userId(model.getUserId().value())
                .build();
    }
}
