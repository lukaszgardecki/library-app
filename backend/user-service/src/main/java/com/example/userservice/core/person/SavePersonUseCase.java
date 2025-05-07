package com.example.userservice.core.person;

import com.example.userservice.domain.model.person.Person;
import com.example.userservice.domain.ports.out.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SavePersonUseCase {
    private final PersonRepositoryPort personRepository;

    Person execute(Person person) {
        return personRepository.save(person);
    }
}
