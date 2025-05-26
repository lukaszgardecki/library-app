package com.example.statisticsservice.domain.ports.out;

import com.example.statisticsservice.domain.integration.LoanCreationDate;
import com.example.statisticsservice.domain.model.borrower.values.UserId;
import com.example.statisticsservice.domain.model.usermonthlyloans.UserMonthlyLoans;

import java.time.LocalDate;
import java.util.List;

public interface UserMonthlyLoansRepositoryPort {

    List<UserMonthlyLoans> findAllByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);

    void incrementUserLoansCount(UserId userId, LoanCreationDate date);
}
