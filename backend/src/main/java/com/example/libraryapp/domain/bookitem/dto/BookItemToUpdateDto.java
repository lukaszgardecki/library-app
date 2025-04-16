package com.example.libraryapp.domain.bookitem.dto;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.bookitem.model.*;
import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookItemToUpdateDto {
    private Boolean isReferenceOnly;
    private LocalDate borrowed;
    private LocalDate dueDate;
    private BigDecimal price;
    private BookItemStatus status;
    private LocalDate dateOfPurchase;
    private Long bookId;
    private Long rackId;
    private Long shelfId;

    public BookItem toModel() {
        return BookItem.builder()
                .isReferenceOnly(new IsReferenceOnly(isReferenceOnly))
                .borrowedDate(new LoanCreationDate(borrowed != null ? borrowed.atStartOfDay() : null))
                .dueDate(new LoanDueDate(dueDate != null ? dueDate.atStartOfDay() : null))
                .price(new Price(price))
                .status(status)
                .dateOfPurchase(new PurchaseDate(dateOfPurchase))
                .bookId(new BookId(bookId))
                .rackId(new RackId(rackId))
                .shelfId(new ShelfId(shelfId))
                .build();
    }

}