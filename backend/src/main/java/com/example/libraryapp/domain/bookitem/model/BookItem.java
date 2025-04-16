package com.example.libraryapp.domain.bookitem.model;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.ShelfId;
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
