package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.ports.out.BorrowerRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.TreeMap;

@RequiredArgsConstructor
class CountUsersByAgeGroupsUseCase {
    private final BorrowerRepositoryPort borrowerRepository;

    Map<String, Integer> execute() {
        return new TreeMap<>(
                Map.of(
                        "<15", borrowerRepository.countUsersByAgeBetween(0, 15),
                        "16-25", borrowerRepository.countUsersByAgeBetween(16, 25),
                        "26-35", borrowerRepository.countUsersByAgeBetween(26, 35),
                        "36-45", borrowerRepository.countUsersByAgeBetween(36, 45),
                        "46-55", borrowerRepository.countUsersByAgeBetween(46, 55),
                        "56-65", borrowerRepository.countUsersByAgeBetween(56, 65),
                        "66+", borrowerRepository.countUsersByAgeBetween(66, 120)
                )
        );
    }
}
