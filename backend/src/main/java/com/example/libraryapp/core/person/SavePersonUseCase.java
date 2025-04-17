package com.example.libraryapp.core.person;

import com.example.libraryapp.domain.person.model.Person;
import com.example.libraryapp.domain.person.ports.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SavePersonUseCase {
    private final PersonRepositoryPort personRepository;

    Person execute(Person person) {
        return personRepository.save(person);
    }
}
