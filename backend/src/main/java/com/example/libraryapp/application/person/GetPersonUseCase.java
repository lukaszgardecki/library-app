package com.example.libraryapp.application.person;

import com.example.libraryapp.domain.person.exceptions.PersonNotFoundException;
import com.example.libraryapp.domain.person.model.Person;
import com.example.libraryapp.domain.person.ports.PersonRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetPersonUseCase {
    private final PersonRepository personRepository;

    Person execute(Long id) {
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }
}
