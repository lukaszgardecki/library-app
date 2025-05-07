package com.example.userservice.core.person;

import com.example.userservice.domain.model.person.Person;
import com.example.userservice.domain.ports.out.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
class PersonService {
    private final PersonRepositoryPort personRepository;

    Page<Person> getAllByQuery(String query, Pageable pageable) {
        return personRepository.findAllByQuery(query, pageable);
    }

    List<Person> getAllByQuery(String query) {
        return personRepository.findAllByQuery(query);
    }

    long countByAgeBetween(int min, int max) {
        return personRepository.countByAgeBetween(min, max);
    }

    List<Object[]> getCitiesByUserCountDesc(int limit) {
        return personRepository.findCitiesByUserCountDesc(limit);
    }
}
