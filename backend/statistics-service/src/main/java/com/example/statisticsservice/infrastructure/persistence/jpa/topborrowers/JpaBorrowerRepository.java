package com.example.statisticsservice.infrastructure.persistence.jpa.topborrowers;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

interface JpaBorrowerRepository extends JpaRepository<BorrowerEntity, Long> {

    @Query(value = "SELECT * FROM borrowers ORDER BY loans_count DESC LIMIT :limit", nativeQuery = true)
    List<BorrowerEntity> findAll(@Param("limit") int limit);

    @Query("SELECT b FROM BorrowerEntity b WHERE FUNCTION('MONTH', b.lastLoanDate) = :month")
    List<BorrowerEntity> findAllByLastLoanMonth(@Param("month") int month);

    @Query("SELECT COUNT(b) FROM BorrowerEntity b WHERE b.birthday BETWEEN :oldest AND :youngest")
    int countUsersByAgeBetween(@Param("oldest") LocalDate oldest, @Param("youngest") LocalDate youngest);

    @Modifying
    @Query("UPDATE BorrowerEntity c SET c.loansCount = c.loansCount + 1 WHERE c.userId = :userId")
    void incrementLoansCount(@Param("userId") Long userId);

    @Modifying
    @Query("UPDATE BorrowerEntity c SET c.lastLoanDate = :newDate WHERE c.userId = :userId")
    void changeLastLoanDate(@Param("userId") Long userId, @Param("newDate") LocalDate newDate);
}
