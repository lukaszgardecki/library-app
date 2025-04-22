package com.example.catalogservice.bookitem.core;

import com.example.catalogservice.bookitem.domain.exceptions.BookItemException;
import com.example.catalogservice.bookitem.domain.model.BookItem;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.model.BookItemStatus;
import com.example.catalogservice.common.MessageKey;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class VerifyAndGetBookItemForRequestUseCase {
    private final BookItemService bookItemService;

    BookItem execute(BookItemId bookItemId) {
        BookItem bookItem = bookItemService.getBookItemById(bookItemId);
        if (bookItem.getStatus() == BookItemStatus.LOST) {
            throw new BookItemException(MessageKey.BOOK_ITEM_LOST);
        }
        return bookItem;
    }
}
