package com.example.userservice.person.core;

import com.example.userservice.person.domain.dto.PersonDto;
import com.example.userservice.person.domain.model.Person;
import com.example.userservice.person.domain.model.PersonId;
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
    private final DeletePersonUseCase deletePersonUseCase;
    private final CountByAgeBetweenUseCase countByAgeBetweenUseCase;
    private final GetCitiesByUserCountDescUseCase getCitiesByUserCountDescUseCase;

    public Page<PersonDto> getAllByQuery(String query, Pageable pageable) {
        return getAllPersonsByQueryUseCase.execute(query, pageable)
                .map(PersonMapper::toDto);
    }

    public List<PersonDto> getAllByQuery(String query) {
        return getAllPersonsByQueryUseCase.execute(query)
                .stream()
                .map(PersonMapper::toDto)
                .toList();
    }

    public PersonDto getPersonById(PersonId id) {
        Person person = getPersonUseCase.execute(id);
        return PersonMapper.toDto(person);
    }

    public PersonDto save(PersonDto person) {
        Person personToSave = PersonMapper.toModel(person);
        Person savedPerson = savePersonUseCase.execute(personToSave);
        return PersonMapper.toDto(savedPerson);
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
