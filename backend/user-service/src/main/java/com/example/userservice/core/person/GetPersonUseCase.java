package com.example.userservice.core.person;

import com.example.userservice.domain.exception.PersonNotFoundException;
import com.example.userservice.domain.model.person.Person;
import com.example.userservice.domain.model.person.PersonId;
import com.example.userservice.domain.ports.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetPersonUseCase {
    private final PersonRepositoryPort personRepository;

    Person execute(PersonId id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}
