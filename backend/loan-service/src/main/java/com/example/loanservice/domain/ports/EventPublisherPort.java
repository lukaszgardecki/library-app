package com.example.loanservice.domain.ports;

import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.model.*;

public interface EventPublisherPort {

    void publishLoanCreatedEvent(BookItemLoanDto bookItemLoan, RequestId requestId, Boolean isReferenceOnly);
    void publishLoanProlongationNotAllowedEvent(BookItemId bookItemId, UserId userId);
    void publishLoanProlongedEvent(BookItemLoanDto bookItemLoan);
    void publishBookItemReturnedEvent(BookItemLoanDto bookItemLoan);
    void publishBookItemLostEvent(BookItemLoanDto bookItemLoan, BookId bookId, Price charge);
}
