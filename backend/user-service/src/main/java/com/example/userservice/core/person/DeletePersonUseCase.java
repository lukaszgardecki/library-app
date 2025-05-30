package com.example.userservice.core.person;

import com.example.userservice.domain.model.person.values.PersonId;
import com.example.userservice.domain.ports.out.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeletePersonUseCase {
    private final PersonRepositoryPort personRepository;

    void execute(PersonId personId) {
        personRepository.deleteById(personId);
    }
}
