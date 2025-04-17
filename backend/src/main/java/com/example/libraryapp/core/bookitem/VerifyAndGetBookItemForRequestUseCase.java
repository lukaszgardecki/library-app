package com.example.libraryapp.core.bookitem;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.bookitemrequest.exceptions.BookItemRequestException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class VerifyAndGetBookItemForRequestUseCase {
    private final BookItemService bookItemService;

    BookItem execute(BookItemId bookItemId) {
        BookItem bookItem = bookItemService.getBookItemById(bookItemId);
        if (bookItem.getStatus() == BookItemStatus.LOST) {
            throw new BookItemRequestException(MessageKey.REQUEST_CREATION_FAILED_BOOK_ITEM_LOST);
        }
        return bookItem;
    }
}
