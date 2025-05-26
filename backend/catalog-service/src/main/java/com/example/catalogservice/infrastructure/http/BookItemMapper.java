package com.example.catalogservice.infrastructure.http;

import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.bookitem.BookItem;
import com.example.catalogservice.domain.model.bookitem.values.*;
import com.example.catalogservice.infrastructure.http.dto.BookItemDto;
import com.example.catalogservice.infrastructure.http.dto.BookItemToSaveDto;
import com.example.catalogservice.infrastructure.http.dto.BookItemToUpdateDto;

class BookItemMapper {

    static BookItemDto toDto(BookItem model) {
        return new BookItemDto(
                model.getId().value(),
                model.getBarcode().value(),
                model.getIsReferenceOnly().value(),
                model.getBorrowedDate().value() != null ? model.getBorrowedDate().value().toLocalDate() : null,
                model.getDueDate().value() != null ? model.getDueDate().value() : null,
                model.getPrice().value(),
                model.getStatus(),
                model.getDateOfPurchase().value(),
                model.getBookId().value(),
                model.getRackId().value(),
                model.getShelfId().value()
        );
    }

    static BookItem toModel(BookItemDto dto) {
        return new BookItem(
                new BookItemId(dto.getId()),
                new BookItemBarcode(dto.getBarcode()),
                new IsReferenceOnly(dto.getIsReferenceOnly()),
                new LoanCreationDate(dto.getBorrowed() != null ? dto.getBorrowed().atStartOfDay() : null),
                new LoanDueDate(dto.getDueDate() != null ? dto.getDueDate() : null),
                new Price(dto.getPrice()),
                dto.getStatus(),
                new PurchaseDate(dto.getDateOfPurchase()),
                new BookId(dto.getBookId()),
                new RackId(dto.getRackId()),
                new ShelfId(dto.getShelfId())
        );
    }

    static BookItem toModel(BookItemToSaveDto dto) {
        return BookItem.builder()
                .isReferenceOnly(new IsReferenceOnly(dto.getIsReferenceOnly()))
                .price(new Price(dto.getPrice()))
                .status(BookItemStatus.AVAILABLE)
                .dateOfPurchase(new PurchaseDate(dto.getDateOfPurchase()))
                .bookId(new BookId(dto.getBookId()))
                .rackId(new RackId(dto.getRackId()))
                .shelfId(new ShelfId(dto.getShelfId()))
                .build();
    }

    static BookItem toModel(BookItemToUpdateDto dto) {
        return BookItem.builder()
                .isReferenceOnly(new IsReferenceOnly(dto.getIsReferenceOnly()))
                .price(new Price(dto.getPrice()))
                .status(BookItemStatus.AVAILABLE)
                .dateOfPurchase(new PurchaseDate(dto.getDateOfPurchase()))
                .bookId(new BookId(dto.getBookId()))
                .rackId(new RackId(dto.getRackId()))
                .shelfId(new ShelfId(dto.getShelfId()))
                .build();
    }
}