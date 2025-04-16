package com.example.libraryapp.application.person;

import com.example.libraryapp.domain.person.model.PersonId;
import com.example.libraryapp.domain.person.ports.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeletePersonUseCase {
    private final PersonRepositoryPort personRepository;

    void execute(PersonId personId) {
        personRepository.deleteById(personId);
    }
}
