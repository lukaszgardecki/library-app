package com.example.statisticsservice.domain.ports.in;

import com.example.statisticsservice.domain.integration.*;
import com.example.statisticsservice.domain.model.borrower.values.PersonFirstName;
import com.example.statisticsservice.domain.model.borrower.values.PersonLastName;
import com.example.statisticsservice.domain.model.borrower.values.UserId;

public interface EventListenerPort {


    void handleUserCreated(UserId userId, PersonFirstName firstName, PersonLastName lastName, BirthDate birthday, City cityName);

    void handleLoanCreated(UserId userId, LoanCreationDate loanCreationDate, Subject subject);

    void handleBookItemReturned(LoanReturnDate returnDate);
}
