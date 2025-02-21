package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.fine.FineFacade;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.event.types.bookitem.BookItemLostEvent;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
class ProcessLostBookItemUseCase {
    private final AuthenticationFacade authFacade;
    private final BookItemFacade bookItemFacade;
    private final BookFacade bookFacade;
    private final BookItemLoanService bookItemLoanService;
    private final FineFacade fineFacade;
    private final EventPublisherPort publisher;

    void execute(Long bookItemId, Long userId) {
        authFacade.validateAdminAccess();
        BookItemLoan bookItemLoan = bookItemLoanService.getBookItemLoan(bookItemId, userId, BookItemLoanStatus.CURRENT);
        LocalDateTime now = LocalDateTime.now();
        bookItemLoan.setReturnDate(now);
        bookItemLoan.setStatus(BookItemLoanStatus.COMPLETED);
        bookItemLoanService.save(bookItemLoan);
        BookItemDto bookItem = bookItemFacade.getBookItem(bookItemId);
        BigDecimal bookItemPrice = bookItem.getPrice();
        fineFacade.processBookItemLost(BookItemLoanMapper.toDto(bookItemLoan), bookItemPrice);
        String bookTitle = bookFacade.getBook(bookItem.getBookId()).getTitle();
        publisher.publish(new BookItemLostEvent(bookItemId, userId, bookTitle, bookItemPrice));
    }
}
