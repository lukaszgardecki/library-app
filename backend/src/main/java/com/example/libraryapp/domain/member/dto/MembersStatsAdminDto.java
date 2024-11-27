package com.example.libraryapp.domain.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MembersStatsAdminDto {
    private long todayLendings;
    private long activeUsersThisMonth;
    private int newUsersThisMonth;
    private long usersCount;
}
