package com.example.statisticsservice.infrastructure.persistence.jpa.dailystats;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

interface JpaDailyStatsRepository extends JpaRepository<DailyStatsEntity, Long> {

    Optional<DailyStatsEntity> findByDate(@Param("date") LocalDate date);

    @Query(value = "SELECT * FROM daily_stats WHERE MONTH(date) = :month", nativeQuery = true)
    List<DailyStatsEntity> findAllByMonthValue(@Param("month") int month);

    List<DailyStatsEntity> findAllByDateBetween(LocalDate start, LocalDate end);

    @Query("""
        SELECT SUM(d.newUsers)
        FROM DailyStatsEntity d
        WHERE d.date >= :start AND d.date <= :end
    """)
    Integer sumNewUsersForMonth(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Modifying
    @Query("UPDATE DailyStatsEntity c SET c.newUsers = c.newUsers + 1 WHERE c.date = :date")
    int incrementNewUsersCount(@Param("date") LocalDate date);

    @Modifying
    @Query("UPDATE DailyStatsEntity c SET c.newUsers = c.newUsers - 1 WHERE c.date = :date")
    int decrementNewUsersCount(@Param("date") LocalDate date);

    @Modifying
    @Query("UPDATE DailyStatsEntity c SET c.newLoans = c.newLoans + 1 WHERE c.date = :date")
    int incrementNewLoansCount(@Param("date") LocalDate date);

    @Modifying
    @Query("UPDATE DailyStatsEntity c SET c.returnedLoans = c.returnedLoans + 1 WHERE c.date = :date")
    int incrementReturnedLoansCount(@Param("date") LocalDate date);

}
