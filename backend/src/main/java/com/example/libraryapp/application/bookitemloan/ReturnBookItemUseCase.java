package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.fine.FineFacade;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.domain.event.types.bookitem.BookItemReturnedEvent;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
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
        String bookTitle = bookFacade.getBook(bookItemLoan.getBookId()).getTitle();
        publisher.publish(new BookItemReturnedEvent(bookItemId, userId, bookTitle, now));
    }
}
