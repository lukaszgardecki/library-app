package com.example.userservice.person.core;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountByAgeBetweenUseCase {
    private final PersonService personService;

    long execute(int min, int max) {
        return personService.countByAgeBetween(min, max);
    }
}
