package com.example.libraryapp.application.statistics;

import com.example.libraryapp.domain.bookitemloan.model.LoanStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
class CountDailyLoansLastWeekUseCase {
    private final StatisticsService statisticsService;

    List<Long> execute(LoanStatus status) {
        LocalDate now = LocalDate.now();
        LocalDate startDate = now.minusDays(6);
        List<Object[]> rawCounts = statisticsService.countBookItemLoansByDay(startDate, now, status);
        Map<Integer, Long> counts = rawCounts.stream()
                .collect(Collectors.toMap(
                        row -> ((Number) row[0]).intValue(),
                        row -> ((Number) row[1]).longValue()
                ));

        return IntStream.rangeClosed(1, 7)
                .mapToObj(i -> now.minusDays(7 - i).getDayOfWeek().getValue())
                .map(day -> counts.getOrDefault(day, 0L))
                .collect(Collectors.toList());
    }
}
