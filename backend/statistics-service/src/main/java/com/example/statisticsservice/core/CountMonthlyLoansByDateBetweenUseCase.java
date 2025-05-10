package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.model.dailystats.DailyStats;
import com.example.statisticsservice.domain.ports.out.DailyStatsRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class CountMonthlyLoansByDateBetweenUseCase {
    private final DailyStatsRepositoryPort dailyStatsRepository;

    Map<Integer, Integer> execute(LocalDate start, LocalDate end) {
        return dailyStatsRepository.findAllByDateBetween(start, end).stream()
                .collect(Collectors.toMap(
                        e -> e.getDate().getMonthValue(),
                        DailyStats::getNewLoans,
                        Integer::sum
                ));
    }
}
