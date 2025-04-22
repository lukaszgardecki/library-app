package com.example.libraryapp.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class StatisticsDto {
    private long todayLoans;
    private long activeUsersThisMonth;
    private long newUsersThisMonth;
    private long usersCount;
    private Map<String, Long> favGenres;
    private List<Long> loansLastYearByMonth;
    private List<Long> newLoansLastWeekByDay;
    private List<Long> returnedLoansLastWeekByDay;
    private List<UserTopBorrowersDto> topBorrowers;
    private Map<String, Long> ageGroups;
    private Map<String, Long> topCities;
}
