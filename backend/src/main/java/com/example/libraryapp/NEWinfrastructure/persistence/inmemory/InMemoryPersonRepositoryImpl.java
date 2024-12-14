package com.example.libraryapp.NEWinfrastructure.persistence.inmemory;

import com.example.libraryapp.NEWdomain.user.model.Person;
import com.example.libraryapp.NEWdomain.user.ports.PersonRepository;

import java.util.concurrent.ConcurrentHashMap;

import static java.util.Objects.requireNonNull;

public class InMemoryPersonRepositoryImpl implements PersonRepository {
    private final ConcurrentHashMap<Long, Person> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Person save(Person personToSave) {
        requireNonNull(personToSave, "Person to save cannot be null");
        if (personToSave.getId() == null) {
            personToSave.setId(++id);
        }
        map.put(personToSave.getId(), personToSave);
        return personToSave;
    }
}
