package com.example.libraryapp.core.statistics;

import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
class CountUserLoansPerMonthUseCase {
    private final StatisticsService statisticsService;

    List<Integer> execute(UserId userId) {
        List<Integer> loansPerMonth = new ArrayList<>();
        YearMonth currentMonth = YearMonth.now();
        Map<YearMonth, Long> loanMap = statisticsService.getUserLoansPerMonth(userId);

        for (int i = 11; i >= 0; i--) {
            YearMonth month = currentMonth.minusMonths(i);
            int count = loanMap.getOrDefault(month, 0L).intValue();
            loansPerMonth.add(count);
        }
        return loansPerMonth;
    }
}
