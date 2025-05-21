package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.model.genre.Genre;
import com.example.statisticsservice.domain.ports.out.GenreRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class GetTopGenresUseCase {
    private final GenreRepositoryPort genreRepository;

    Map<String, Integer> execute(int limit) {
        return genreRepository.findAllOrderByLoansDesc(limit).stream()
                .collect(Collectors.toMap(
                        Genre::getName,
                        Genre::getLoans
                ));
    }
}
