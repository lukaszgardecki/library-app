package com.example.libraryapp.application.librarycard;

import com.example.libraryapp.domain.librarycard.dto.LibraryCardDto;
import com.example.libraryapp.domain.librarycard.model.IssuedDate;
import com.example.libraryapp.domain.librarycard.model.LibraryCard;
import com.example.libraryapp.domain.librarycard.model.LibraryCardBarcode;
import com.example.libraryapp.domain.librarycard.model.LibraryCardId;
import com.example.libraryapp.domain.user.model.UserId;

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
