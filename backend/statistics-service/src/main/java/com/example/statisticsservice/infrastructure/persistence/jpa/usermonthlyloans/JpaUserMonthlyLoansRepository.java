package com.example.statisticsservice.infrastructure.persistence.jpa.usermonthlyloans;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

interface JpaUserMonthlyLoansRepository extends JpaRepository<UserMonthlyLoansEntity, Long> {

    @Query("""
        SELECT u FROM UserMonthlyLoansEntity u
        WHERE u.userId = :userId
          AND (u.yearValue > :#{#start.year} OR (u.yearValue = :#{#start.year} AND u.monthValue >= :#{#start.monthValue}))
          AND (u.yearValue < :#{#end.year} OR (u.yearValue = :#{#end.year} AND u.monthValue <= :#{#end.monthValue}))
    """)
    List<UserMonthlyLoansEntity> findAllByUserIdAndDateBetween(
            @Param("userId") Long userId,
            @Param("start") LocalDate start,
            @Param("end") LocalDate end
    );

    @Modifying
    @Query("""
        UPDATE UserMonthlyLoansEntity c
        SET c.loansCount = c.loansCount + 1
        WHERE c.userId = :userId
          AND c.monthValue = :#{#date.monthValue}
          AND c.yearValue = :#{#date.year}
    """)
    int incrementUserLoansCount(@Param("userId") Long userId, @Param("date") LocalDate date);

}
