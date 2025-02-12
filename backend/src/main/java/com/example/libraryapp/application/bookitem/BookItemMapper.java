package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.model.BookItem;

class BookItemMapper {

    static BookItemDto toDto(BookItem model) {
        return new BookItemDto(
                model.getId(),
                model.getBarcode(),
                model.getIsReferenceOnly(),
                model.getBorrowed(),
                model.getDueDate(),
                model.getPrice(),
                model.getFormat(),
                model.getStatus(),
                model.getDateOfPurchase(),
                model.getPublicationDate(),
                model.getBookId(),
                model.getRackId()
        );
    }

    static BookItem toModel(BookItemDto dto) {
        return new BookItem(
                dto.getId(),
                dto.getBarcode(),
                dto.getIsReferenceOnly(),
                dto.getBorrowed(),
                dto.getDueDate(),
                dto.getPrice(),
                dto.getFormat(),
                dto.getStatus(),
                dto.getDateOfPurchase(),
                dto.getPublicationDate(),
                dto.getBookId(),
                dto.getRackId()
        );
    }
}
