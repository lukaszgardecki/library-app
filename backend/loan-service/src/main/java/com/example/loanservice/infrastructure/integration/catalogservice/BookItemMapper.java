package com.example.loanservice.infrastructure.integration.catalogservice;

import com.example.loanservice.domain.integration.catalogservice.book.values.BookId;
import com.example.loanservice.domain.integration.catalogservice.bookitem.BookItem;
import com.example.loanservice.domain.integration.catalogservice.bookitem.values.*;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.LoanCreationDate;
import com.example.loanservice.domain.model.values.LoanDueDate;
import com.example.loanservice.infrastructure.integration.catalogservice.dto.BookItemDto;

class BookItemMapper {

    static BookItemDto toDto(BookItem model) {
        return new BookItemDto(
                model.getId().value(),
                model.getBarcode().value(),
                model.getIsReferenceOnly().value(),
                model.getBorrowedDate().value() != null ? model.getBorrowedDate().value().toLocalDate() : null,
                model.getDueDate().value() != null ? model.getDueDate().value() : null,
                model.getPrice().value(),
                model.getStatus().name(),
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
                BookItemStatus.valueOf(dto.getStatus()),
                new PurchaseDate(dto.getDateOfPurchase()),
                new BookId(dto.getBookId()),
                new RackId(dto.getRackId()),
                new ShelfId(dto.getShelfId())
        );
    }
}