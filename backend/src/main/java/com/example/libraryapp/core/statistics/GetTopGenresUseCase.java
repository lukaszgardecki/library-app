package com.example.libraryapp.core.statistics;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
class GetTopGenresUseCase {
    private final  StatisticsService statisticsService;

    Map<String, Long> execute() {
        int limit = 5;
        return statisticsService.getTopSubjectsWithLoansCount(limit);
    }
}
