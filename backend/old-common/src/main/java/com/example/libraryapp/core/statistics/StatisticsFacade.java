package com.example.libraryapp.core.statistics;

import com.example.libraryapp.domain.bookitemloan.model.LoanStatus;
import com.example.libraryapp.domain.statistics.dto.StatisticsDto;
import com.example.libraryapp.domain.user.model.UserId;
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
          countDailyLoansLastWeekUseCase.execute(LoanStatus.CURRENT),
          countDailyLoansLastWeekUseCase.execute(LoanStatus.COMPLETED),
          getTopBorrowersUseCase.execute(),
          countUsersByAgeGroupsUseCase.execute(),
          getTopCitiesByUserCountUseCase.execute()
        );
    }

    public List<Integer> countUserLoansPerMonth(UserId userId) {
        return countUserLoansPerMonthUseCase.execute(userId);
    }
}
