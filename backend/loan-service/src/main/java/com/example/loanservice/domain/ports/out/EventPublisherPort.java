package com.example.loanservice.domain.ports.out;

import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.integration.catalog.BookId;
import com.example.loanservice.domain.integration.catalog.Price;
import com.example.loanservice.domain.integration.request.RequestId;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.UserId;

public interface EventPublisherPort {

    void publishLoanCreatedEvent(BookItemLoanDto bookItemLoan, RequestId requestId, Boolean isReferenceOnly);
    void publishLoanProlongationNotAllowedEvent(BookItemId bookItemId, UserId userId);
    void publishLoanProlongedEvent(BookItemLoanDto bookItemLoan);
    void publishBookItemReturnedEvent(BookItemLoanDto bookItemLoan);
    void publishBookItemLostEvent(BookItemLoanDto bookItemLoan, BookId bookId, Price charge);
}
