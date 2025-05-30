package com.example.statisticsservice.infrastructure.persistence.jpa.dailystats;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "daily_stats")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class DailyStatsEntity {
    @Id
    private LocalDate date;
    private int newLoans;
    private int returnedLoans;
    private int newUsers;
}
