package com.example.libraryapp.NEWapplication.bookitem;

import com.example.libraryapp.NEWapplication.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItemStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class UpdateBookItemAfterReturnUseCase {
    private final BookItemRequestFacade bookItemRequestFacade;
    private final BookItemService bookItemService;

    void execute(Long bookItemId, LocalDateTime returnDate) {
        BookItem bookItem = bookItemService.findBookItem(bookItemId);
        boolean bookIsReserved = bookItemRequestFacade.isBookItemRequested(bookItem.getId());

        if (bookIsReserved) {
            bookItem.setStatus(BookItemStatus.REQUESTED);
        } else {
            bookItem.setStatus(BookItemStatus.AVAILABLE);
        }

        bookItem.setDueDate(returnDate.toLocalDate());
        bookItemService.saveBookItem(bookItem);
    }
}
