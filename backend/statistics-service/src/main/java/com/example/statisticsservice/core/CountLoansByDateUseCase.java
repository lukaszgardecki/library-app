package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.model.dailystats.DailyStats;
import com.example.statisticsservice.domain.ports.out.DailyStatsRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
class CountLoansByDateUseCase {
    private final DailyStatsRepositoryPort dailyStatsRepository;

    int execute(LocalDate date) {
        return dailyStatsRepository.findByDate(date)
                .map(DailyStats::getNewLoans)
                .orElse(0);
    }
}
