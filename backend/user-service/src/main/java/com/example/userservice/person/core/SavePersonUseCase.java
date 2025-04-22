package com.example.userservice.person.core;

import com.example.userservice.person.domain.model.Person;
import com.example.userservice.person.domain.ports.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SavePersonUseCase {
    private final PersonRepositoryPort personRepository;

    Person execute(Person person) {
        return personRepository.save(person);
    }
}
