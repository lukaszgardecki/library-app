package com.example.userservice.core.person;

import com.example.userservice.domain.model.person.Person;
import com.example.userservice.domain.model.person.values.PersonId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdatePersonUseCase {
    private final PersonService personService;

    void execute(PersonId personId, Person person) {

    }
}
