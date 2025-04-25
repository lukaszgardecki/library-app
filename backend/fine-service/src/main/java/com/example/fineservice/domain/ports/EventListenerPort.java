package com.example.fineservice.domain.ports;

import com.example.fineservice.domain.model.*;

public interface EventListenerPort {

    void handleBookItemReturnedEvent(LoanId loanId, UserId userId, LoanReturnDate returnDate, LoanDueDate dueDate);

    void handleBookItemLostEvent(LoanId loanId, UserId userId, LoanReturnDate returnDate, LoanDueDate dueDate, Price charge);
}
