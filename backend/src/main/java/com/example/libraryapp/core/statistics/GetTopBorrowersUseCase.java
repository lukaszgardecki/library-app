package com.example.libraryapp.core.statistics;

import com.example.libraryapp.domain.statistics.dto.UserTopBorrowersDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetTopBorrowersUseCase {
    private final StatisticsService statisticsService;

    List<UserTopBorrowersDto> execute() {
        int limit = 10;
        return statisticsService.getTopBorrowers(limit);
    }
}
