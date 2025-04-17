package com.example.libraryapp.core.person;

import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class GetCitiesByUserCountDescUseCase {
    private final PersonService personService;

    Map<String, Long> execute(int limit) {
        return personService.getCitiesByUserCountDesc(limit)
                .stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> ((Number) row[1]).longValue()
                ));
    }
}
