package com.example.statisticsservice.domain.model.dailystats;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyStats {
    private LocalDate date;
    private int newLoans;
    private int returnedLoans;
    private int newUsers;
}
