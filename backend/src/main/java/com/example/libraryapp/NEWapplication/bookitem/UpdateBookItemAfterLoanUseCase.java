package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItemStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class UpdateBookItemAfterLoanUseCase {
    private final BookItemService bookItemService;

    void execute(Long bookItemId, LocalDateTime borrowedDate, LocalDateTime dueDate) {
        BookItem bookItem = bookItemService.findBookItem(bookItemId);
        bookItem.setStatus(BookItemStatus.LOANED);
        bookItem.setBorrowed(borrowedDate.toLocalDate());
        bookItem.setDueDate(dueDate.toLocalDate());
        bookItemService.saveBookItem(bookItem);
    }
}
