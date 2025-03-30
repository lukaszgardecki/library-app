package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.book.model.PublicationDate;
import com.example.libraryapp.domain.bookitem.dto.BookItemToUpdateDto;
import com.example.libraryapp.domain.bookitem.model.*;
import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.rack.model.RackId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateBookItemUseCase {
    private final BookItemService bookItemService;

    BookItem execute(BookItemId id, BookItemToUpdateDto fields) {
        BookItem bookItemToUpdate = bookItemService.getBookItemById(id);
        BookItem updatedBookItem = updateFields(fields, bookItemToUpdate);
        return bookItemService.save(updatedBookItem);
    }

    private BookItem updateFields(BookItemToUpdateDto fields, BookItem itemToUpdate) {
        BookItem model = fields.toModel();
        if (fields.getIsReferenceOnly() != null) itemToUpdate.setIsReferenceOnly(new IsReferenceOnly(fields.getIsReferenceOnly()));
        if (fields.getBorrowed() != null) itemToUpdate.setBorrowedDate(new LoanCreationDate(fields.getBorrowed().atStartOfDay()));
        if (fields.getDueDate() != null) itemToUpdate.setDueDate(new LoanDueDate(fields.getDueDate().atStartOfDay()));
        if (fields.getPrice() != null) itemToUpdate.setPrice(new Price(fields.getPrice()));
        if (fields.getStatus() != null) itemToUpdate.setStatus(fields.getStatus());
        if (fields.getDateOfPurchase() != null) itemToUpdate.setDateOfPurchase(new PurchaseDate(fields.getDateOfPurchase()));
        if (fields.getBookId() != null) itemToUpdate.setBookId(new BookId(fields.getBookId()));
        if (fields.getRackId() != null) itemToUpdate.setRackId(new RackId(fields.getRackId()));
        return model;
    }
}
