package com.example.libraryapp.application.statistics;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@RequiredArgsConstructor
class CountUsersByAgeGroupsUseCase {
    private final StatisticsService statisticsService;

    Map<String, Long> execute() {
        return new TreeMap<>(
                Map.of(
                        "<15", statisticsService.countUsersByAgeBetween(0, 15),
                        "16-25", statisticsService.countUsersByAgeBetween(16, 25),
                        "26-35", statisticsService.countUsersByAgeBetween(26, 35),
                        "36-45", statisticsService.countUsersByAgeBetween(36, 45),
                        "46-55", statisticsService.countUsersByAgeBetween(46, 55),
                        "56-65", statisticsService.countUsersByAgeBetween(56, 65),
                        "66+", statisticsService.countUsersByAgeBetween(66, 120)
                )
        );
    }
}
