package com.example.libraryapp.NEWapplication.bookitemloan;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWapplication.book.BookFacade;
import com.example.libraryapp.NEWapplication.bookitem.BookItemFacade;
import com.example.libraryapp.NEWapplication.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.NEWapplication.constants.Constants;
import com.example.libraryapp.NEWapplication.fine.FineFacade;
import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemRenewedEvent;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class RenewBookItemLoanUseCase {
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
        userFacade.verifyUserForBookItemRenewal(userId);
        fineFacade.validateUserForFines(userId);
        bookItemRequestFacade.ensureBookItemNotRequestedUseCase(bookItemId);
        BookItemLoan loanToUpdate = bookItemLoanService.getBookItemLoan(bookItemId, userId, BookItemLoanStatus.CURRENT);
        bookItemLoanService.validateBookItemLoanForRenewal(loanToUpdate);
        loanToUpdate.setDueDate(LocalDateTime.now().plusDays(Constants.MAX_LENDING_DAYS));
        BookItemLoan savedLoan = bookItemLoanService.save(loanToUpdate);
        bookItemFacade.updateBookItemAfterRenewal(bookItemId, savedLoan.getDueDate());
        String bookTitle = bookFacade.getBook(savedLoan.getBookId()).getTitle();
        publisher.publish(new BookItemRenewedEvent(bookItemId, userId, bookTitle));
        return savedLoan;
    }
}
