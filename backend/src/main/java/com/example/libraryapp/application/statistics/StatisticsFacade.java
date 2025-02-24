package com.example.libraryapp.application.statistics;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.domain.statistics.dto.StatisticsDto;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class StatisticsFacade {
    private final GetTopGenresUseCase getTopGenresUseCase;
    private final GetTopBorrowersUseCase getTopBorrowersUseCase;
    private final GetTopCitiesByUserCountUseCase getTopCitiesByUserCountUseCase;
    private final CountAllUsersUseCase countAllUsersUseCase;
    private final CountUsersByAgeGroupsUseCase countUsersByAgeGroupsUseCase;
    private final CountNewUsersInCurrentMonthUseCase countNewUsersInCurrentMonthUseCase;
    private final CountActiveUsersInCurrentMonthUseCase countActiveUsersInCurrentMonthUseCase;
    private final CountTodayLoansUseCase countTodayLoansUseCase;
    private final CountMonthlyLoansLastYearUseCase countMonthlyLoansLastYearUseCase;
    private final CountDailyLoansLastWeekUseCase countDailyLoansLastWeekUseCase;
    private final CountUserLoansPerMonthUseCase countUserLoansPerMonthUseCase;

    public StatisticsDto getStatistics() {
        return new StatisticsDto(
          countTodayLoansUseCase.execute(),
          countActiveUsersInCurrentMonthUseCase.execute(),
          countNewUsersInCurrentMonthUseCase.execute(),
          countAllUsersUseCase.execute(),
          getTopGenresUseCase.execute(),
          countMonthlyLoansLastYearUseCase.execute(),
          countDailyLoansLastWeekUseCase.execute(BookItemLoanStatus.CURRENT),
          countDailyLoansLastWeekUseCase.execute(BookItemLoanStatus.COMPLETED),
          getTopBorrowersUseCase.execute(),
          countUsersByAgeGroupsUseCase.execute(),
          getTopCitiesByUserCountUseCase.execute()
        );
    }

    public List<Integer> countUserLoansPerMonth(Long userId) {
        return countUserLoansPerMonthUseCase.execute(userId);
    }
}
