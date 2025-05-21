package com.example.statisticsservice.infrastructure.http.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class StatisticsDto {
    private int todayLoans;
    private long activeUsersThisMonth;
    private int newUsersThisMonth;
    private long usersCount;
    private Map<String, Integer> favGenres;
    private Map<Integer, Integer> loansLastYearByMonth;
    private Map<Integer, Integer> newLoansLastWeekByDay;
    private Map<Integer, Integer> returnedLoansLastWeekByDay;
    private List<BorrowerDto> topBorrowers;
    private Map<String, Integer> ageGroups;
    private Map<String, Integer> topCities;
}
