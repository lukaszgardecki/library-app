package com.example.fineservice.domain.ports.in;

import com.example.fineservice.domain.integration.loan.LoanDueDate;
import com.example.fineservice.domain.integration.loan.LoanReturnDate;
import com.example.fineservice.domain.integration.catalog.Price;
import com.example.fineservice.domain.model.values.LoanId;
import com.example.fineservice.domain.model.values.UserId;

public interface EventListenerPort {

    void handleBookItemReturnedEvent(LoanId loanId, UserId userId, LoanReturnDate returnDate, LoanDueDate dueDate);

    void handleBookItemLostEvent(LoanId loanId, UserId userId, LoanReturnDate returnDate, LoanDueDate dueDate, Price charge);
}
