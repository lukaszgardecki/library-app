package com.example.userservice.infrastructure.persistence.jpa.person;

import com.example.userservice.domain.model.person.*;
import com.example.userservice.domain.model.person.values.*;
import com.example.userservice.domain.ports.out.PersonRepositoryPort;
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
    public Optional<Person> findById(PersonId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    @Transactional
    public void deleteById(PersonId id) {
        repository.deleteById(id.value());
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
                model.getId() != null ? model.getId().value() : null,
                model.getFirstName().value(),
                model.getLastName().value(),
                model.getGender(),
                model.getPesel().value(),
                model.getDateOfBirth().value(),
                model.getNationality().value(),
                model.getFathersName().value(),
                model.getMothersName().value(),
                toEntity(model.getAddress()),
                model.getPhone().value()
        );
    }

    private Person toModel(PersonEntity entity) {
        return new Person(
                new PersonId(entity.getId()),
                new PersonFirstName(entity.getFirstName()),
                new PersonLastName(entity.getLastName()),
                entity.getGender(),
                new Pesel(entity.getPesel()),
                new BirthDate(entity.getDateOfBirth()),
                new Nationality(entity.getNationality()),
                new FatherName(entity.getFathersName()),
                new MotherName(entity.getMothersName()),
                toModel(entity.getAddress()),
                new PhoneNumber(entity.getPhone())
        );
    }

    private AddressEntity toEntity(Address address) {
        return new AddressEntity(
                address.getStreetAddress().value(),
                address.getCity().value(),
                address.getState().value(),
                address.getZipCode().value(),
                address.getCountry().value()
        );
    }

    private Address toModel(AddressEntity address) {
        return new Address(
                new Address.StreetAddress(address.getStreetAddress()),
                new Address.City(address.getCity()),
                new Address.State(address.getState()),
                new Address.ZipCode(address.getZipCode()),
                new Address.Country(address.getCountry())
        );
    }
}
