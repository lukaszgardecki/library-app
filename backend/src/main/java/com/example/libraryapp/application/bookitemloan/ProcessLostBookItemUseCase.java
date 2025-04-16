package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.fine.FineFacade;
import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitem.model.Price;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.LoanReturnDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanStatus;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.event.types.bookitem.BookItemLostEvent;
import com.example.libraryapp.domain.user.model.UserId;
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

    void execute(BookItemId bookItemId, UserId userId) {
        authFacade.validateAdminAccess();
        BookItemLoan bookItemLoan = bookItemLoanService.getBookItemLoan(bookItemId, userId, LoanStatus.CURRENT);
        bookItemLoan.setReturnDate(new LoanReturnDate(LocalDateTime.now()));
        bookItemLoan.setStatus(LoanStatus.COMPLETED);
        bookItemLoanService.save(bookItemLoan);
        BookItemDto bookItem = bookItemFacade.getBookItem(bookItemId);
        Price bookItemPrice = new Price(bookItem.getPrice());
        fineFacade.processBookItemLost(BookItemLoanMapper.toDto(bookItemLoan), bookItemPrice);
        String bookTitle = bookFacade.getBook(new BookId(bookItem.getBookId())).getTitle();
        publisher.publish(new BookItemLostEvent(bookItemId, userId, new Title(bookTitle), bookItemPrice));
    }
}
