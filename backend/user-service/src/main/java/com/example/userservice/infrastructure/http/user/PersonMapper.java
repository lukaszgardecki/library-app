package com.example.userservice.infrastructure.http.user;


import com.example.userservice.domain.model.person.Person;
import com.example.userservice.domain.model.person.values.*;
import com.example.userservice.infrastructure.http.user.dto.AddressDto;
import com.example.userservice.infrastructure.http.user.dto.PersonDto;
import com.example.userservice.infrastructure.http.user.dto.RegisterUserDto;

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
                                .streetAddress(new StreetAddress(dto.getAddress() != null ? dto.getAddress().getStreetAddress() : null))
                                .city(new City(dto.getAddress() != null ? dto.getAddress().getCity() : null))
                                .state(new State(dto.getAddress() != null ? dto.getAddress().getState() : null))
                                .zipCode(new ZipCode(dto.getAddress() != null ? dto.getAddress().getZipCode() : null))
                                .country(new Country(dto.getAddress() != null ? dto.getAddress().getCountry() : null))
                                .build()
                )
                .phone(new PhoneNumber(dto.getPhone()))
                .build();
    }

    static Person toModel(RegisterUserDto dto) {
        return Person.builder()
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
                                .streetAddress(new StreetAddress(dto.getStreetAddress()))
                                .zipCode(new ZipCode(dto.getZipCode()))
                                .city(new City(dto.getCity()))
                                .state(new State(dto.getState()))
                                .country(new Country(dto.getCountry()))
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
