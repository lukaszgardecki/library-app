package com.example.libraryapp.core.bookitemloan;

import com.example.libraryapp.core.auth.AuthenticationFacade;
import com.example.libraryapp.core.book.BookFacade;
import com.example.libraryapp.core.bookitem.BookItemFacade;
import com.example.libraryapp.core.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.core.fine.FineFacade;
import com.example.libraryapp.core.user.UserFacade;
import com.example.libraryapp.domain.Constants;
import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanStatus;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.event.types.bookitem.BookItemRenewedEvent;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class RenewBookItemLoanUseCase {
    private final UserFacade userFacade;
    private final AuthenticationFacade authFacade;
    private final BookItemFacade bookItemFacade;
    private final BookItemRequestFacade bookItemRequestFacade;
    private final BookFacade bookFacade;
    private final BookItemLoanService bookItemLoanService;
    private final FineFacade fineFacade;
    private final EventPublisherPort publisher;

    BookItemLoan execute(BookItemId bookItemId, UserId userId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        userFacade.verifyUserForBookItemRenewal(userId);
        fineFacade.validateUserForFines(userId);
        bookItemRequestFacade.ensureBookItemNotRequestedUseCase(bookItemId);
        BookItemLoan loanToUpdate = bookItemLoanService.getBookItemLoan(bookItemId, userId, LoanStatus.CURRENT);
        bookItemLoanService.validateBookItemLoanForRenewal(loanToUpdate);
        loanToUpdate.setDueDate(new LoanDueDate(LocalDateTime.now().plusDays(Constants.MAX_LENDING_DAYS)));
        BookItemLoan savedLoan = bookItemLoanService.save(loanToUpdate);
        BookItemDto bookItem = bookItemFacade.getBookItem(bookItemId);
        String bookTitle = bookFacade.getBook(new BookId(bookItem.getBookId())).getTitle();
        publisher.publish(new BookItemRenewedEvent(bookItemId, userId, new Title(bookTitle), savedLoan.getDueDate()));
        return savedLoan;
    }
}
