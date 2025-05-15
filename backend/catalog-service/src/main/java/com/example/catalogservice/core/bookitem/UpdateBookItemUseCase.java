package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.model.bookitem.BookItem;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateBookItemUseCase {
    private final BookItemService bookItemService;

    BookItem execute(BookItemId id, BookItem fields) {
        BookItem bookItemToUpdate = bookItemService.getBookItemById(id);
        updateFields(fields, bookItemToUpdate);
        return bookItemService.save(bookItemToUpdate);
    }

    private void updateFields(BookItem fields, BookItem itemToUpdate) {
        if (fields.getIsReferenceOnly().value() != null) itemToUpdate.setIsReferenceOnly(fields.getIsReferenceOnly());
        if (fields.getPrice().value() != null) itemToUpdate.setPrice(fields.getPrice());
        if (fields.getStatus() != null) itemToUpdate.setStatus(fields.getStatus());
        if (fields.getDateOfPurchase().value() != null) itemToUpdate.setDateOfPurchase(fields.getDateOfPurchase());
        if (fields.getBookId().value() != null) itemToUpdate.setBookId(fields.getBookId());
        if (fields.getRackId().value() != null) itemToUpdate.setRackId(fields.getRackId());
        if (fields.getShelfId().value() != null) itemToUpdate.setShelfId(fields.getShelfId());
    }
}
