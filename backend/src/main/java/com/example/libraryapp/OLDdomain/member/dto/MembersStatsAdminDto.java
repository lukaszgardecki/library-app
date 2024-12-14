package com.example.libraryapp.OLDdomain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class MembersStatsAdminDto {
    private long todayLendings;
    private long activeUsersThisMonth;
    private int newUsersThisMonth;
    private long usersCount;
    private Map<String, Long> favGenres;
    private List<Long> lendingsLastYearByMonth;
    private List<Long> newLendingsLastWeekByDay;
    private List<Long> returnedLendingsLastWeekByDay;
    private List<MemberTopBorrowersDtoAdmin> topBorrowers;
    private Map<String, Long> ageGroups;
    private Map<String, Long> topCities;
}
