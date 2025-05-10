package com.example.statisticsservice.infrastructure.persistence.jpa.cities;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

interface JpaCityRepository extends JpaRepository<CityEntity, Long> {

    @Query(value = "SELECT * FROM cities ORDER BY users DESC LIMIT :limit", nativeQuery = true)
    List<CityEntity> findAllOrderByUsersDesc(@Param("limit") int limit);

    @Modifying
    @Query("UPDATE CityEntity c SET c.users = c.users + 1 WHERE c.name = :city")
    int incrementUsersCount(@Param("city") String city);

    @Modifying
    @Query("UPDATE CityEntity c SET c.users = c.users - 1 WHERE c.name = :city")
    int decrementUsersCount(@Param("city") String city);

}
