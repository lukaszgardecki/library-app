package com.example.userservice.person.core;

import com.example.userservice.person.domain.exceptions.PersonNotFoundException;
import com.example.userservice.person.domain.model.Person;
import com.example.userservice.person.domain.model.PersonId;
import com.example.userservice.person.domain.ports.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetPersonUseCase {
    private final PersonRepositoryPort personRepository;

    Person execute(PersonId id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}
