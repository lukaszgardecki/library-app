package com.example.libraryapp.NEWapplication.bookitemloan;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWapplication.book.BookFacade;
import com.example.libraryapp.NEWapplication.bookitem.BookItemFacade;
import com.example.libraryapp.NEWapplication.fine.FineFacade;
import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemReturnedEvent;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class ReturnBookItemUseCase {
    private final UserFacade userFacade;
    private final AuthenticationFacade authFacade;
    private final BookItemFacade bookItemFacade;
    private final BookFacade bookFacade;
    private final BookItemLoanService bookItemLoanService;
    private final FineFacade fineFacade;
    private final EventPublisherPort publisher;

    void execute(Long bookItemId, Long userId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        BookItemLoan bookItemLoan = bookItemLoanService.getBookItemLoan(bookItemId, userId, BookItemLoanStatus.CURRENT);
        LocalDateTime now = LocalDateTime.now();
        bookItemLoan.setReturnDate(now);
        bookItemLoan.setStatus(BookItemLoanStatus.COMPLETED);
        bookItemLoanService.save(bookItemLoan);
        fineFacade.processBookItemReturn(BookItemLoanMapper.toDto(bookItemLoan));
        bookItemFacade.updateBookItemAfterReturn(bookItemId, now);
        userFacade.updateUserAfterBookItemReturn(userId);
        String bookTitle = bookFacade.getBook(bookItemLoan.getBookId()).getTitle();
        publisher.publish(new BookItemReturnedEvent(bookItemId, userId, bookTitle));
    }
}
