package com.example.libraryapp.infrastructure.persistence.jpa.person;

import com.example.libraryapp.domain.person.model.Address;
import com.example.libraryapp.domain.person.model.Person;
import com.example.libraryapp.domain.person.ports.PersonRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class PersonRepositoryAdapter implements PersonRepositoryPort {
    private final JpaPersonRepository repository;

    @Override
    @Transactional
    public Person save(Person personToSave) {
        PersonEntity savedPerson = repository.save(toEntity(personToSave));
        return toModel(savedPerson);
    }

    @Override
    public Page<Person> findAllByQuery(String query, Pageable pageable) {
        return repository.findAllByQuery(query, pageable)
                .map(this::toModel);
    }

    @Override
    public List<Person> findAllByQuery(String query) {
        return repository.findAllByQuery(query)
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Optional<Person> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public long countByAgeBetween(int min, int max) {
        return repository.countByAgeBetween(min, max);
    }

    @Override
    public List<Object[]> findCitiesByUserCountDesc(int limit) {
        return repository.findCitiesByUserCountDesc(limit);
    }

    private PersonEntity toEntity(Person model) {
        return new PersonEntity(
                model.getId(),
                model.getFirstName(),
                model.getLastName(),
                model.getGender(),
                model.getPesel(),
                model.getDateOfBirth(),
                model.getNationality(),
                model.getFathersName(),
                model.getMothersName(),
                toEntity(model.getAddress()),
                model.getPhone()
        );
    }

    private Person toModel(PersonEntity entity) {
        return new Person(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getGender(),
                entity.getPesel(),
                entity.getDateOfBirth(),
                entity.getNationality(),
                entity.getFathersName(),
                entity.getMothersName(),
                toModel(entity.getAddress()),
                entity.getPhone()
        );
    }

    private AddressEntity toEntity(Address address) {
        return new AddressEntity(
                address.getStreetAddress(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getCountry()
        );
    }

    private Address toModel(AddressEntity address) {
        return new Address(
                address.getStreetAddress(),
                address.getCity(),
                address.getState(),
                address.getZipCode(),
                address.getCountry()
        );
    }
}
