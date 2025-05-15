package com.example.userservice.core.person;

import com.example.userservice.domain.model.person.Person;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SavePersonUseCase {
    private final PersonService personService;

    Person execute(Person person) {
        return personService.save(person);
    }
}
