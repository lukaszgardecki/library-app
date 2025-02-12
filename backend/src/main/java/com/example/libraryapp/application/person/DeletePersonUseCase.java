package com.example.libraryapp.application.person;

import com.example.libraryapp.domain.person.ports.PersonRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeletePersonUseCase {
    private final PersonRepository personRepository;

    void execute(Long personId) {
        personRepository.deleteById(personId);
    }
}
