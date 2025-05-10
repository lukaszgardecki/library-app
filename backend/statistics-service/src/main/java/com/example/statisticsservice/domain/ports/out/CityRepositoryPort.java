package com.example.statisticsservice.domain.ports.out;

import com.example.statisticsservice.domain.model.city.City;

import java.util.List;

public interface CityRepositoryPort {

    List<City> findAllOrderByUsersDesc(int limit);

    void incrementUsersCount(String city);

    void decrementUsersCount(String city);
}
