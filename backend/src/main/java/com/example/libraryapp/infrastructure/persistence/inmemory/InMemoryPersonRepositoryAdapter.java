package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.person.model.Person;
import com.example.libraryapp.domain.person.ports.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class InMemoryPersonRepositoryAdapter implements PersonRepository {
    private final ConcurrentHashMap<Long, Person> map = new ConcurrentHashMap<>();
    private static long id = 0;

    @Override
    public Person save(Person personToSave) {
        requireNonNull(personToSave, "Person to save cannot be null");
        if (personToSave.getId() == null) {
            personToSave.setId(++id);
        }
        return map.put(personToSave.getId(), personToSave);
    }

    @Override
    public Page<Person> findAllByQuery(String query, Pageable pageable) {
        List<Person> filteredPeople = map.values().stream()
                .filter(person -> {
                    if (query == null || query.isEmpty()) {
                        return true;
                    }
                    String lowerQuery = query.toLowerCase();
                    return person.getFirstName().toLowerCase().contains(lowerQuery) ||
                            person.getLastName().toLowerCase().contains(lowerQuery);
                })
               .toList();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), filteredPeople.size());
        List<Person> pageContent = filteredPeople.subList(start, end);
        return new PageImpl<>(pageContent, pageable, filteredPeople.size());
    }

    @Override
    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void deleteById(Long id) {
        map.remove(id);
    }

    @Override
    public long countByAgeBetween(int min, int max) {
        LocalDate currentDate = LocalDate.now();
        return map.values().stream()
                .map(Person::getDateOfBirth)
                .map(dob -> Period.between(dob, currentDate).getYears())
                .filter(age -> age >= min && age <= max)
                .count();
    }

    @Override
    public List<Object[]> findCitiesByUserCountDesc(int limit) {
        Map<String, Long> cityCounts = map.values().stream()
                .collect(Collectors.groupingBy(person -> person.getAddress().getCity(), Collectors.counting()));
        return cityCounts.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .map(entry -> new Object[] { entry.getKey(), entry.getValue() })
                .limit(limit)
                .toList();
    }
}
