package com.example.statisticsservice.domain.ports.out;

import com.example.statisticsservice.domain.integration.LoanCreationDate;
import com.example.statisticsservice.domain.integration.LoanReturnDate;
import com.example.statisticsservice.domain.model.dailystats.DailyStats;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyStatsRepositoryPort {

    Optional<DailyStats> findByDate(LocalDate date);

    List<DailyStats> findAllByMonthValue(int month);

    List<DailyStats> findAllByDateBetween(LocalDate start, LocalDate end);

    void incrementNewUsersCount(LocalDate date);
    void decrementNewUsersCount(LocalDate date);

    void incrementNewLoansCount(LoanCreationDate date);

    void incrementReturnedLoansCount(LoanReturnDate date);
}
