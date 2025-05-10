package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.model.dailystats.DailyStats;
import com.example.statisticsservice.domain.ports.out.DailyStatsRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountNewUsersByMonthUseCase {
    private final DailyStatsRepositoryPort dailyStatsRepository;

    int execute(int month) {
        return dailyStatsRepository.findAllByMonthValue(month).stream()
                .mapToInt(DailyStats::getNewUsers)
                .sum();
    }
}
