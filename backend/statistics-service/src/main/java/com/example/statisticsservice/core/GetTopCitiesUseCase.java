package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.model.city.City;
import com.example.statisticsservice.domain.ports.out.CityRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class GetTopCitiesUseCase {
    private final CityRepositoryPort citiesRepository;

    Map<String, Integer> execute(int limit) {
        return citiesRepository.findAllOrderByUsersDesc(limit).stream()
                .collect(Collectors.toMap(
                        City::getName,
                        City::getUsers
                ));
    }
}
