package com.example.statisticsservice.domain.ports.in;

import com.example.statisticsservice.domain.integration.LoanCreationDate;
import com.example.statisticsservice.domain.integration.LoanReturnDate;
import com.example.statisticsservice.domain.integration.Subject;
import com.example.statisticsservice.domain.model.borrower.values.PersonFirstName;
import com.example.statisticsservice.domain.model.borrower.values.PersonLastName;
import com.example.statisticsservice.domain.model.borrower.values.UserId;

import java.time.LocalDate;

public interface EventListenerPort {


    void handleUserCreated(UserId userId, PersonFirstName firstName, PersonLastName lastName, LocalDate birthday, String cityName);

    void handleLoanCreated(UserId userId, LoanCreationDate loanCreationDate, Subject subject);

    void handleBookItemReturned(LoanReturnDate returnDate);
}
