package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.application.fine.FineFacade;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitem.model.IsReferenceOnly;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.model.RequestId;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.event.types.bookitem.BookItemLoanedEvent;
import com.example.libraryapp.domain.user.model.UserId;
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

    BookItemLoan execute(BookItemId bookItemId, UserId userId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        userFacade.verifyUserForBookItemLoan(userId);
        fineFacade.validateUserForFines(userId);
        BookItemDto bookItem = bookItemFacade.verifyAndGetBookItemForLoan(bookItemId);
        RequestId bookItemRequestId = bookItemRequestFacade
                .checkIfBookItemRequestStatusIsReady(bookItemId, userId);
        BookItemLoan bookItemLoan = bookItemLoanService.saveLoan(bookItemId, userId, new BookId(bookItem.getBookId()));
        bookItemRequestFacade.changeBookItemRequestStatus(bookItemRequestId, BookItemRequestStatus.COMPLETED);
        String bookTitle = bookFacade.getBook(new BookId(bookItem.getBookId())).getTitle();
        publisher.publish(
                new BookItemLoanedEvent(
                        bookItemId, userId,
                        new Title(bookTitle),
                        new IsReferenceOnly(bookItem.getIsReferenceOnly()),
                        bookItemLoan.getCreationDate(), bookItemLoan.getDueDate()
                )
        );
        return bookItemLoan;
    }
}
