package com.example.libraryapp.application.person;

import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

@RequiredArgsConstructor
public class PersonFacade {
    private final GetPageByQueryUseCase getPageByQueryUseCase;
    private final GetPersonUseCase getPersonUseCase;
    private final SavePersonUseCase savePersonUseCase;
    private final DeletePersonUseCase deletePersonUseCase;
    private final CountByAgeBetweenUseCase countByAgeBetweenUseCase;
    private final GetCitiesByUserCountDescUseCase getCitiesByUserCountDescUseCase;

    public Page<PersonDto> getAllByQuery(String query, Pageable pageable) {
        return getPageByQueryUseCase.execute(query, pageable)
                .map(PersonMapper::toDto);
    }

    public PersonDto getPersonById(Long id) {
        Person person = getPersonUseCase.execute(id);
        return PersonMapper.toDto(person);
    }

    public PersonDto save(PersonDto person) {
        Person personToSave = PersonMapper.toModel(person);
        Person savedPerson = savePersonUseCase.execute(personToSave);
        return PersonMapper.toDto(savedPerson);
    }

    public void deletePerson(Long id) {
        deletePersonUseCase.execute(id);
    }

    public long countByAgeBetween(int min, int max) {
        return countByAgeBetweenUseCase.execute(min ,max);
    }

    public Map<String, Long> getCitiesByUserCountDesc(int limit) {
        return getCitiesByUserCountDescUseCase.execute(limit);
    }
}
