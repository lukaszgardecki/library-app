package com.example.libraryapp.NEWinfrastructure.persistence.jpa.person;

import com.example.libraryapp.NEWdomain.user.model.Address;
import com.example.libraryapp.NEWdomain.user.model.Person;
import com.example.libraryapp.NEWdomain.user.ports.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
class PersonRepositoryImpl implements PersonRepository {
    private final JpaPersonRepository repository;

    @Override
    @Transactional
    public Person save(Person personToSave) {
        PersonEntity savedPerson = repository.save(toEntity(personToSave));
        return toModel(savedPerson);
    }

    private PersonEntity toEntity(Person dto) {
        return new PersonEntity(
                dto.getId(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getGender(),
                dto.getPesel(),
                dto.getDateOfBirth(),
                dto.getNationality(),
                dto.getFathersName(),
                dto.getMothersName(),
                toEntity(dto.getAddress()),
                dto.getPhone()
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
