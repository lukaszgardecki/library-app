package com.example.libraryapp.application.person;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountByAgeBetweenUseCase {
    private final PersonService personService;

    long execute(int min, int max) {
        return personService.countByAgeBetween(min, max);
    }
}
