package com.example.libraryapp.core.statistics;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
class GetTopCitiesByUserCountUseCase {
    private final StatisticsService statisticsService;


    Map<String, Long> execute() {
        int limit = 10;
        return statisticsService.getTopCitiesByUserCount(limit);
    }
}
