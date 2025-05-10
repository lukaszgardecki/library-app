package com.example.statisticsservice.domain.ports.out;

import com.example.statisticsservice.domain.integration.LoanCreationDate;
import com.example.statisticsservice.domain.model.borrower.Borrower;
import com.example.statisticsservice.domain.model.borrower.values.UserId;

import java.time.LocalDate;
import java.util.List;

public interface BorrowerRepositoryPort {

    long count();

    int countUsersByAgeBetween(int fromAge, int toAge);

    List<Borrower> getBorrowers(int limit);

    List<Borrower> findAllByLastLoanMonth(int month);

    void saveNewBorrower(Borrower borrower);

    void incrementLoansCount(UserId userId);

    void changeLastLoanDate(UserId userId, LoanCreationDate newDate);
}
