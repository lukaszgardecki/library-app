package com.example.loanservice.domain.integration.catalogservice.bookitem;

import com.example.loanservice.domain.integration.catalogservice.book.values.BookId;
import com.example.loanservice.domain.integration.catalogservice.bookitem.values.*;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.LoanCreationDate;
import com.example.loanservice.domain.model.values.LoanDueDate;
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
