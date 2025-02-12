package com.example.libraryapp.application.statistics;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
class CountMonthlyLoansLastYearUseCase {
    private final StatisticsService statisticsService;

    List<Long> execute() {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusMonths(11).withDayOfMonth(1);
        List<Object[]> rawCounts = statisticsService.countBookItemLoansByMonth(startDate, now);

        Map<Integer, Long> monthCounts = rawCounts.stream()
                .collect(Collectors.toMap(
                        row -> (Integer) row[0],
                        row -> (Long) row[1]
                ));

        return IntStream.rangeClosed(0, 11)
                .mapToObj(i -> now.minusMonths(11 - i).getMonthValue())
                .map(month -> monthCounts.getOrDefault(month, 0L))
                .collect(Collectors.toList());
    }
}
