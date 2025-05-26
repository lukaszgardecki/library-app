package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.ports.out.DailyStatsRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
class CountDailyNewLoansByDateBetweenUseCase {
    private final DailyStatsRepositoryPort dailyStatsRepository;

    Map<Integer, Integer> execute() {
        Map<Integer, Integer> newLoans = new HashMap<>();
        LocalDate now = LocalDate.now();
        LocalDate from = now.minusDays(6);
        LocalDate counter = from;

        while (!counter.isAfter(now)) {
            newLoans.put(counter.getDayOfWeek().getValue(), 0);
            counter = counter.plusDays(1);
        }

        dailyStatsRepository.findAllByDateBetween(from, now)
                .forEach(dailyStat -> {
                    int day = dailyStat.getDate().getDayOfWeek().getValue();
                    newLoans.put(day, newLoans.get(day) + dailyStat.getNewLoans());
                });
        return newLoans;
    }
}
