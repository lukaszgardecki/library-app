package com.example.statisticsservice.infrastructure.persistence.jpa.genres;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface JpaGenreRepository extends JpaRepository<GenreEntity, Long> {

    @Query(value = "SELECT * FROM genres ORDER BY loans DESC LIMIT :limit", nativeQuery = true)
    List<GenreEntity> findAllOrderByLoansDesc(@Param("limit") int limit);

    @Modifying
    @Query("UPDATE GenreEntity c SET c.loans = c.loans + 1 WHERE c.name = :genre")
    int incrementLoansCount(@Param("genre") String genre);

    @Modifying
    @Query("UPDATE GenreEntity c SET c.loans = c.loans - 1 WHERE c.name = :genre")
    int decrementLoansCount(@Param("genre") String genre);

}
