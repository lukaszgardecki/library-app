package com.example.libraryapp.application.person;

import com.example.libraryapp.domain.person.model.Person;
import com.example.libraryapp.domain.person.ports.PersonRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class SavePersonUseCase {
    private final PersonRepository personRepository;

    Person execute(Person person) {
        return personRepository.save(person);
    }
}
