package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.dto.BookDto;
import com.example.catalogservice.domain.model.book.BookId;
import com.example.catalogservice.domain.dto.BookItemDto;
import com.example.catalogservice.domain.dto.BookItemWithBookDto;
import com.example.catalogservice.domain.dto.RackDto;
import com.example.catalogservice.domain.dto.ShelfDto;
import com.example.catalogservice.domain.model.bookitem.*;

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
                model.getRackId().value(),
                model.getShelfId().value()
        );
    }

    static BookItemWithBookDto toDto(BookItem bookItem, BookDto book, RackDto rack, ShelfDto shelf) {
        return new BookItemWithBookDto(
                bookItem.getId().value(),
                bookItem.getBarcode().value(),
                bookItem.getIsReferenceOnly().value(),
                bookItem.getBorrowedDate().value() != null ? bookItem.getBorrowedDate().value().toLocalDate() : null,
                bookItem.getDueDate().value() != null ? bookItem.getDueDate().value().toLocalDate() : null,
                bookItem.getPrice().value(),
                bookItem.getStatus(),
                bookItem.getDateOfPurchase().value(),
                book,
                bookItem.getRackId().value(),
                rack.getName(),
                bookItem.getShelfId().value(),
                shelf.getName()
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
                new RackId(dto.getRackId()),
                new ShelfId(dto.getShelfId())
        );
    }
}
