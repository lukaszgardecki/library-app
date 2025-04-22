package com.example.userservice.person.core;

import com.example.userservice.person.domain.model.PersonId;
import com.example.userservice.person.domain.ports.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeletePersonUseCase {
    private final PersonRepositoryPort personRepository;

    void execute(PersonId personId) {
        personRepository.deleteById(personId);
    }
}
