package com.example.loanservice.domain.ports.out;

import com.example.loanservice.domain.integration.catalogservice.book.values.BookId;
import com.example.loanservice.domain.integration.catalogservice.bookitem.values.IsReferenceOnly;
import com.example.loanservice.domain.integration.catalogservice.bookitem.values.Price;
import com.example.loanservice.domain.integration.requestservice.RequestId;
import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.UserId;

public interface EventPublisherPort {

    void publishLoanCreatedEvent(BookItemLoan bookItemLoan, RequestId requestId, IsReferenceOnly isReferenceOnly);
    void publishLoanProlongationNotAllowedEvent(BookItemId bookItemId, UserId userId);
    void publishLoanProlongedEvent(BookItemLoan bookItemLoan);
    void publishBookItemReturnedEvent(BookItemLoan bookItemLoan);
    void publishBookItemLostEvent(BookItemLoan bookItemLoan, BookId bookId, Price charge);
}
