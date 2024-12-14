package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItemStatus;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateBookItemAfterBookRequestUseCase {
    private final BookItemService bookItemService;

    void execute(Long bookItemId) {
        BookItem bookItem = bookItemService.findBookItem(bookItemId);
        if (bookItem.getStatus() == BookItemStatus.AVAILABLE) {
            bookItemService.updateBookItemStatus(bookItemId, BookItemStatus.REQUESTED);
        }
    }
}
