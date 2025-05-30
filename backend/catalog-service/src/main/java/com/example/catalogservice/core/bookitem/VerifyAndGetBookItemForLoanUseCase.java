package com.example.catalogservice.core.bookitem;

import com.example.catalogservice.domain.exception.BookItemException;
import com.example.catalogservice.domain.i18n.MessageKey;
import com.example.catalogservice.domain.model.bookitem.BookItem;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.model.bookitem.values.BookItemStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class VerifyAndGetBookItemForLoanUseCase {
    private final BookItemService bookItemService;

    BookItem execute(BookItemId bookItemId) {
        BookItem bookItem = bookItemService.getBookItemById(bookItemId);
        if (bookItem.getStatus() == BookItemStatus.LOST) {
            throw new BookItemException(MessageKey.BOOK_ITEM_LOST);
        }
        return bookItem;
    }
}
