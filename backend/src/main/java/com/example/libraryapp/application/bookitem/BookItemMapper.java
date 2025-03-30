package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.book.model.PublicationDate;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.model.*;
import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.rack.model.RackId;

class BookItemMapper {

    static BookItemDto toDto(BookItem model) {
        return new BookItemDto(
                model.getId().value(),
                model.getBarcode().value(),
                model.getIsReferenceOnly().value(),
                model.getBorrowedDate().value() != null ? model.getBorrowedDate().value().toLocalDate() : null,
                model.getDueDate().value() != null ? model.getDueDate().value().toLocalDate() : null,
                model.getPrice().value(),
                model.getStatus(),
                model.getDateOfPurchase().value(),
                model.getBookId().value(),
                model.getRackId().value()
        );
    }

    static BookItem toModel(BookItemDto dto) {
        return new BookItem(
                new BookItemId(dto.getId()),
                new BookItemBarcode(dto.getBarcode()),
                new IsReferenceOnly(dto.getIsReferenceOnly()),
                new LoanCreationDate(dto.getBorrowed() != null ? dto.getBorrowed().atStartOfDay() : null),
                new LoanDueDate(dto.getDueDate() != null ? dto.getDueDate().atStartOfDay() : null),
                new Price(dto.getPrice()),
                dto.getStatus(),
                new PurchaseDate(dto.getDateOfPurchase()),
                new BookId(dto.getBookId()),
                new RackId(dto.getRackId())
        );
    }
}
