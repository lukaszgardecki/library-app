package com.example.loanservice.core;

import com.example.loanservice.domain.integration.catalogservice.book.values.BookId;
import com.example.loanservice.domain.integration.catalogservice.bookitem.BookItem;
import com.example.loanservice.domain.integration.catalogservice.bookitem.values.Price;
import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.LoanReturnDate;
import com.example.loanservice.domain.model.values.LoanStatus;
import com.example.loanservice.domain.model.values.UserId;
import com.example.loanservice.domain.ports.out.CatalogServicePort;
import com.example.loanservice.domain.ports.out.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class ProcessLostBookItemUseCase {
    private final CatalogServicePort catalogService;
    private final BookItemLoanService bookItemLoanService;
    private final EventPublisherPort publisher;

    void execute(BookItemId bookItemId, UserId userId) {
        BookItemLoan bookItemLoan = bookItemLoanService.getBookItemLoan(bookItemId, userId, LoanStatus.CURRENT);
        bookItemLoan.setReturnDate(new LoanReturnDate(LocalDateTime.now()));
        bookItemLoan.setStatus(LoanStatus.COMPLETED);
        bookItemLoanService.save(bookItemLoan);
        BookItem bookItem = catalogService.getBookItemById(bookItemId);
        Price bookItemPrice = bookItem.getPrice();
        BookId bookId = catalogService.getBookIdByBookItemId(bookItemId);
        publisher.publishBookItemLostEvent(bookItemLoan, bookId, bookItemPrice);
    }
}
