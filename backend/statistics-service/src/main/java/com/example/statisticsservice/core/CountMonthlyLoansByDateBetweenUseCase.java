package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.ports.out.DailyStatsRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
class CountMonthlyLoansByDateBetweenUseCase {
    private final DailyStatsRepositoryPort dailyStatsRepository;

    Map<Integer, Integer> execute(LocalDate start, LocalDate end) {
        Map<Integer, Integer> monthlyLoans = new HashMap<>();
        LocalDate current = start;

        while (!current.isAfter(end)) {
            monthlyLoans.put(current.getMonthValue(), 0);
            current = current.plusMonths(1);
        }

        dailyStatsRepository.findAllByDateBetween(start, end)
                .forEach(dailyStat -> {
                    int month = dailyStat.getDate().getMonthValue();
                    monthlyLoans.compute(month, (key, oldValue) -> (oldValue == null ? dailyStat.getNewLoans() : oldValue + dailyStat.getNewLoans()));
                });

        return monthlyLoans;
    }

}
