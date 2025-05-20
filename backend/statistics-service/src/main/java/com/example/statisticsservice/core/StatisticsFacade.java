package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.model.borrower.Borrower;
import com.example.statisticsservice.domain.model.borrower.values.UserId;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class StatisticsFacade {
    private final GetTopBorrowersUseCase getTopBorrowersUseCase;
    private final GetTopCitiesUseCase getTopCitiesUseCase;
    private final GetTopGenresUseCase getTopGenresUseCase;
    private final CountAllUsersUseCase countAllUsersUseCase;
    private final CountNewUsersByMonthUseCase countNewUsersByMonthUseCase;
    private final CountLoansByDateUseCase countLoansByDateUseCase;
    private final CountDailyNewLoansByDateBetweenUseCase countDailyNewLoansByDateBetweenUseCase;
    private final CountDailyReturnedLoansLastWeekUseCase countDailyReturnedLoansLastWeekUseCase;
    private final CountMonthlyLoansByDateBetweenUseCase countMonthlyLoansByDateBetweenUseCase;
    private final CountMonthlyUserLoansByDateUseCase countMonthlyUserLoansByDateUseCase;
    private final CountActiveBorrowersByMonthUseCase countActiveBorrowersByMonthUseCase;
    private final CountUsersByAgeGroupsUseCase countUsersByAgeGroupsUseCase;

    public List<Borrower> getTop10BorrowersDesc() {
        return getTopBorrowersUseCase.execute(10);
    }

    public Map<String, Integer> getTop5Genres() {
        return getTopGenresUseCase.execute(5);
    }

    public Map<String, Integer> getTop10Cities() {
        return getTopCitiesUseCase.execute(10);
    }

    public int countTodayLoans() {
        return countLoansByDateUseCase.execute(LocalDate.now());
    }

    public Map<Integer, Integer> countDailyNewLoansLast7days() {
        return countDailyNewLoansByDateBetweenUseCase.execute();
    }

    public Map<Integer, Integer> countDailyReturnedLoansLast7days() {
        return countDailyReturnedLoansLastWeekUseCase.execute();
    }

    public Map<Integer, Integer> countMonthlyLoansLast12Months() {
        LocalDate now = LocalDate.now();
        return countMonthlyLoansByDateBetweenUseCase.execute(now.minusMonths(11), now);
    }

    public long countAllUsers() {
        return countAllUsersUseCase.execute();
    }

    public int countNewUsersInCurrentMonth() {
        return countNewUsersByMonthUseCase.execute(LocalDate.now().getMonthValue());
    }

    public long countActiveBorrowersInCurrentMonth() {
        int monthValue = LocalDate.now().getMonthValue();
        return countActiveBorrowersByMonthUseCase.execute(monthValue);
    }

    public Map<String, Integer> countUsersByAgeGroups() {
        return countUsersByAgeGroupsUseCase.execute();
    }

    public Map<Integer, Integer> countMonthlyUserLoansLast12Months(UserId userId) {
        LocalDate now = LocalDate.now();
        return countMonthlyUserLoansByDateUseCase.execute(userId, now.minusMonths(12), now);
    }
}
