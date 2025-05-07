package com.example.userservice.core.person;


import com.example.userservice.domain.dto.person.AddressDto;
import com.example.userservice.domain.dto.person.PersonDto;
import com.example.userservice.domain.model.person.*;
import com.example.userservice.domain.model.person.values.*;

class PersonMapper {

    static Person toModel(PersonDto dto) {
        return Person.builder()
                .id(new PersonId(dto.getId()))
                .firstName(new PersonFirstName(dto.getFirstName()))
                .lastName(new PersonLastName(dto.getLastName()))
                .gender(dto.getGender())
                .pesel(new Pesel(dto.getPesel()))
                .dateOfBirth(new BirthDate(dto.getDateOfBirth()))
                .nationality(new Nationality(dto.getNationality()))
                .fathersName(new FatherName(dto.getFathersName()))
                .mothersName(new MotherName(dto.getMothersName()))
                .address(
                        Address.builder()
                                .streetAddress(new Address.StreetAddress(dto.getAddress() != null ? dto.getAddress().getStreetAddress() : null))
                                .city(new Address.City(dto.getAddress() != null ? dto.getAddress().getCity() : null))
                                .state(new Address.State(dto.getAddress() != null ? dto.getAddress().getState() : null))
                                .zipCode(new Address.ZipCode(dto.getAddress() != null ? dto.getAddress().getZipCode() : null))
                                .country(new Address.Country(dto.getAddress() != null ? dto.getAddress().getCountry() : null))
                                .build()
                )
                .phone(new PhoneNumber(dto.getPhone()))
                .build();
    }

    static PersonDto toDto(Person model) {
        return PersonDto.builder()
                .id(model.getId().value())
                .firstName(model.getFirstName().value())
                .lastName(model.getLastName().value())
                .gender(model.getGender())
                .pesel(model.getPesel().value())
                .dateOfBirth(model.getDateOfBirth().value())
                .nationality(model.getNationality().value())
                .fathersName(model.getFathersName().value())
                .mothersName(model.getMothersName().value())
                .address(
                        AddressDto.builder()
                                .streetAddress(model.getAddress().getStreetAddress().value())
                                .city(model.getAddress().getCity().value())
                                .state(model.getAddress().getState().value())
                                .zipCode(model.getAddress().getZipCode().value())
                                .country(model.getAddress().getCountry().value())
                                .build()
                )
                .phone(model.getPhone().value())
                .build();
    }
}
