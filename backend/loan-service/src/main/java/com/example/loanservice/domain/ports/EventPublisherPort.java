package com.example.loanservice.domain.ports;

import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.model.BookItemId;
import com.example.loanservice.domain.model.Price;
import com.example.loanservice.domain.model.RequestId;
import com.example.loanservice.domain.model.UserId;

public interface EventPublisherPort {

    void publishBookItemLoanedEvent(BookItemLoanDto bookItemLoan, RequestId requestId);
    void publishBookItemRenewalImpossibleEvent(BookItemId bookItemId, UserId userId);
    void publishBookItemRenewedEvent(BookItemLoanDto bookItemLoan);
    void publishBookItemReturnedEvent(BookItemLoanDto bookItemLoan);
    void publishBookItemLostEvent(BookItemLoanDto bookItemLoan, Price charge);
}
