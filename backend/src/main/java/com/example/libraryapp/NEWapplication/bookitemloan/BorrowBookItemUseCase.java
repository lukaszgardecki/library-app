package com.example.libraryapp.NEWapplication.bookitemloan;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWapplication.book.BookFacade;
import com.example.libraryapp.NEWapplication.bookitem.BookItemFacade;
import com.example.libraryapp.NEWapplication.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.NEWapplication.fine.FineFacade;
import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.bookitem.dto.BookItemDto;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemLoanedEvent;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class BorrowBookItemUseCase {
    private final UserFacade userFacade;
    private final AuthenticationFacade authFacade;
    private final BookItemRequestFacade bookItemRequestFacade;
    private final BookItemFacade bookItemFacade;
    private final BookFacade bookFacade;
    private final BookItemLoanService bookItemLoanService;
    private final FineFacade fineFacade;
    private final EventPublisherPort publisher;

    BookItemLoan execute(Long userId, Long bookItemId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        userFacade.verifyUserForBookItemLoan(userId);
        fineFacade.validateUserForFines(userId);
        BookItemDto bookItem = bookItemFacade.verifyAndGetBookItemForLoan(bookItemId);
        Long bookItemRequestId = bookItemRequestFacade
                .checkBookItemRequestStatus(bookItemId, userId, BookItemRequestStatus.READY);
        BookItemLoan bookItemLoan = bookItemLoanService.saveLoan(bookItemId, userId, bookItem.getBookId());
        userFacade.updateUserAfterBookItemLoan(userId);
        bookItemFacade.updateBookItemAfterLoan(bookItemId, bookItemLoan.getCreationDate(), bookItemLoan.getDueDate());
        bookItemRequestFacade.changeBookItemRequestStatus(bookItemRequestId, BookItemRequestStatus.COMPLETED);
        String bookTitle = bookFacade.getBook(bookItemLoan.getBookId()).getTitle();
        publisher.publish(new BookItemLoanedEvent(bookItemId, userId, bookTitle));
        return bookItemLoan;
    }
}
