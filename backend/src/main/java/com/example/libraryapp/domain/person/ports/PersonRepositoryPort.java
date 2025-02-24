package com.example.libraryapp.domain.person.ports;

import com.example.libraryapp.domain.person.model.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonRepositoryPort {
    Person save(Person personToSave);

    Page<Person> findAllByQuery(String query, Pageable pageable);

    List<Person> findAllByQuery(String query);

    Optional<Person> findById(Long id);

    void deleteById(Long id);

    long countByAgeBetween(int min, int max);

    List<Object[]> findCitiesByUserCountDesc(int limit);
}
