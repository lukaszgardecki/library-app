package com.example.statisticsservice.infrastructure.http;

import com.example.statisticsservice.core.StatisticsFacade;
import com.example.statisticsservice.domain.model.borrower.values.UserId;
import com.example.statisticsservice.infrastructure.http.dto.StatisticsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/stats")
@RequiredArgsConstructor
class StatisticsController {
    private final StatisticsFacade statisticsFacade;

    @GetMapping
    ResponseEntity<StatisticsDto> getStatistics() {
        StatisticsDto statistics = new StatisticsDto(
                statisticsFacade.countTodayLoans(),
                statisticsFacade.countActiveBorrowersInCurrentMonth(),
                statisticsFacade.countNewUsersInCurrentMonth(),
                statisticsFacade.countAllUsers(),
                statisticsFacade.getTop5Genres(),
                statisticsFacade.countMonthlyLoansLast12Months(),
                statisticsFacade.countDailyNewLoansLast7days(),
                statisticsFacade.countDailyReturnedLoansLast7days(),
                statisticsFacade.getTop10Borrowers().stream().map(BorrowerMapper::toDto).toList(),
                statisticsFacade.countUsersByAgeGroups(),
                statisticsFacade.getTop10Cities()
        );
        return ResponseEntity.ok(statistics);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<Map<Integer, Integer>> countUserLoansPerMonth(@PathVariable Long id) {
        Map<Integer, Integer> loansPerMonth = statisticsFacade.countMonthlyUserLoansLast12Months(new UserId(id));
        return ResponseEntity.ok(loansPerMonth);
    }
}
