package com.example.userservice.core.person;

import com.example.userservice.domain.model.person.Person;
import com.example.userservice.domain.model.person.values.PersonId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PersonFacade {
    private final GetAllPersonsByQueryUseCase getAllPersonsByQueryUseCase;
    private final GetPersonUseCase getPersonUseCase;
    private final SavePersonUseCase savePersonUseCase;
    private final UpdatePersonUseCase updatePersonUseCase;
    private final DeletePersonUseCase deletePersonUseCase;
    private final CountByAgeBetweenUseCase countByAgeBetweenUseCase;
    private final GetCitiesByUserCountDescUseCase getCitiesByUserCountDescUseCase;

    public Page<Person> getAllByQuery(String query, Pageable pageable) {
        return getAllPersonsByQueryUseCase.execute(query, pageable);
    }

    public List<Person> getAllByQuery(String query) {
        return getAllPersonsByQueryUseCase.execute(query);
    }

    public Person getPersonById(PersonId id) {
        return getPersonUseCase.execute(id);
    }

    public Person save(Person person) {
        return savePersonUseCase.execute(person);
    }

    public void updatePerson(PersonId personId, Person person) {
        updatePersonUseCase.execute(personId, person);
    }

    public void deletePerson(PersonId id) {
        deletePersonUseCase.execute(id);
    }

    public long countByAgeBetween(int min, int max) {
        return countByAgeBetweenUseCase.execute(min ,max);
    }

    public Map<String, Long> getCitiesByUserCountDesc(int limit) {
        return getCitiesByUserCountDescUseCase.execute(limit);
    }
}
