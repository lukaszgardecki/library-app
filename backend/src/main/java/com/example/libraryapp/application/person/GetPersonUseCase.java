package com.example.libraryapp.application.person;

import com.example.libraryapp.domain.person.exceptions.PersonNotFoundException;
import com.example.libraryapp.domain.person.model.Person;
import com.example.libraryapp.domain.person.ports.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetPersonUseCase {
    private final PersonRepositoryPort personRepository;

    Person execute(Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }
}
