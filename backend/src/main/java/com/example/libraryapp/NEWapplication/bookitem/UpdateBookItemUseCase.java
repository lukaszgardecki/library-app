package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.dto.BookItemToUpdateDto;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateBookItemUseCase {
    private final BookItemService bookItemService;

    BookItem execute(Long id, BookItemToUpdateDto fields) {
        BookItem bookItemToUpdate = bookItemService.findBookItem(id);
        BookItem updatedBookItem = updateFields(fields, bookItemToUpdate);
        return bookItemService.saveBookItem(updatedBookItem);
    }

    private BookItem updateFields(BookItemToUpdateDto fields, BookItem itemToUpdate) {
        BookItem model = fields.toModel();
        if (fields.getIsReferenceOnly() != null) itemToUpdate.setIsReferenceOnly(fields.getIsReferenceOnly());
        if (fields.getBorrowed() != null) itemToUpdate.setBorrowed(fields.getBorrowed());
        if (fields.getDueDate() != null) itemToUpdate.setDueDate(fields.getDueDate());
        if (fields.getPrice() != null) itemToUpdate.setPrice(fields.getPrice());
        if (fields.getFormat() != null) itemToUpdate.setFormat(fields.getFormat());
        if (fields.getStatus() != null) itemToUpdate.setStatus(fields.getStatus());
        if (fields.getDateOfPurchase() != null) itemToUpdate.setDateOfPurchase(fields.getDateOfPurchase());
        if (fields.getPublicationDate() != null) itemToUpdate.setPublicationDate(fields.getPublicationDate());
        if (fields.getBookId() != null) itemToUpdate.setBookId(fields.getBookId());
        if (fields.getRackId() != null) itemToUpdate.setRackId(fields.getRackId());
        return model;
    }
}
