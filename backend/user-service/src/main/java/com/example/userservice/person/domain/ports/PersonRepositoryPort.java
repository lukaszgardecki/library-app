package com.example.userservice.person.domain.ports;

import com.example.userservice.person.domain.model.Person;
import com.example.userservice.person.domain.model.PersonId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonRepositoryPort {
    Person save(Person personToSave);

    Page<Person> findAllByQuery(String query, Pageable pageable);

    List<Person> findAllByQuery(String query);

    Optional<Person> findById(PersonId id);

    void deleteById(PersonId id);

    long countByAgeBetween(int min, int max);

    List<Object[]> findCitiesByUserCountDesc(int limit);
}
