package com.example.statisticsservice.domain.ports.out;

import com.example.statisticsservice.domain.integration.City;
import com.example.statisticsservice.domain.model.city.CityStats;

import java.util.List;

public interface CityRepositoryPort {

    List<CityStats> findAllOrderByUsersDesc(int limit);

    void incrementUsersCount(City city);

    void decrementUsersCount(City city);
}
