package com.example.catalogservice.bookitem.domain.model;

import com.example.catalogservice.book.domain.model.BookId;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class BookItem {
    private BookItemId id;
    private BookItemBarcode barcode;
    private IsReferenceOnly isReferenceOnly;
    private LoanCreationDate borrowedDate;
    private LoanDueDate dueDate;
    private Price price;
    private BookItemStatus status;
    private PurchaseDate dateOfPurchase;
    private BookId bookId;
    private RackId rackId;
    private ShelfId shelfId;
}
