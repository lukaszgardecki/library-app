package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItemStatus;
import com.example.libraryapp.NEWdomain.bookitemrequest.exceptions.BookItemRequestException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class VerifyAndGetBookItemForRequestUseCase {
    private final BookItemService bookItemService;

    BookItem execute(Long bookItemId) {
        BookItem bookItem = bookItemService.findBookItem(bookItemId);
        if (bookItem.getStatus() == BookItemStatus.LOST) {
            throw new BookItemRequestException("Message.RESERVATION_CREATION_FAILED_BOOK_ITEM_LOST.getMessage()");
        }
        return bookItem;
    }
}
