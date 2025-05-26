package com.example.userservice.infrastructure.http.librarycard;

import com.example.userservice.domain.model.librarycard.LibraryCard;
import com.example.userservice.domain.model.librarycard.values.IssuedDate;
import com.example.userservice.domain.model.librarycard.values.LibraryCardBarcode;
import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.infrastructure.http.librarycard.dto.LibraryCardDto;

public class LibraryCardMapper {

    public static LibraryCard toModel(LibraryCardDto dto) {
        return LibraryCard.builder()
                .id(new LibraryCardId(dto.getId()))
                .barcode(new LibraryCardBarcode(dto.getBarcode()))
                .issuedAt(new IssuedDate(dto.getIssuedAt()))
                .status(dto.getStatus())
                .userId(new UserId(dto.getUserId()))
                .build();
    }

    public static LibraryCardDto toDto(LibraryCard model) {
        return LibraryCardDto.builder()
                .id(model.getId().value())
                .barcode(model.getBarcode().value())
                .issuedAt(model.getIssuedAt().value())
                .status(model.getStatus())
                .userId(model.getUserId().value())
                .build();
    }
}
