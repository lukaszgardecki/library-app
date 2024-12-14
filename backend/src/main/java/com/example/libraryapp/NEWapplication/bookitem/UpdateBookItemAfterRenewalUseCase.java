package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class UpdateBookItemAfterRenewalUseCase {
    private final BookItemService bookItemService;

    void execute(Long bookItemId, LocalDateTime dueDate) {
        BookItem bookItem = bookItemService.findBookItem(bookItemId);
        bookItem.setDueDate(dueDate.toLocalDate());
        bookItemService.saveBookItem(bookItem);
    }
}
