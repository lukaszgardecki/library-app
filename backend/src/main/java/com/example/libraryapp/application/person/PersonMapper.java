package com.example.libraryapp.application.person;

import com.example.libraryapp.domain.person.dto.AddressDto;
import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.person.model.Address;
import com.example.libraryapp.domain.person.model.Person;

class PersonMapper {

    static Person toModel(PersonDto dto) {
        return Person.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .gender(dto.getGender())
                .pesel(dto.getPesel())
                .dateOfBirth(dto.getDateOfBirth())
                .nationality(dto.getNationality())
                .fathersName(dto.getFathersName())
                .mothersName(dto.getMothersName())
                .address(
                        Address.builder()
                                .streetAddress(dto.getAddress().getStreetAddress())
                                .city(dto.getAddress().getCity())
                                .state(dto.getAddress().getState())
                                .zipCode(dto.getAddress().getZipCode())
                                .country(dto.getAddress().getCountry())
                                .build()
                )
                .phone(dto.getPhone())
                .build();
    }

    static PersonDto toDto(Person model) {
        return PersonDto.builder()
                .id(model.getId())
                .firstName(model.getFirstName())
                .lastName(model.getLastName())
                .gender(model.getGender())
                .pesel(model.getPesel())
                .dateOfBirth(model.getDateOfBirth())
                .nationality(model.getNationality())
                .fathersName(model.getFathersName())
                .mothersName(model.getMothersName())
                .address(
                        AddressDto.builder()
                                .streetAddress(model.getAddress().getStreetAddress())
                                .city(model.getAddress().getCity())
                                .state(model.getAddress().getState())
                                .zipCode(model.getAddress().getZipCode())
                                .country(model.getAddress().getCountry())
                                .build()
                )
                .phone(model.getPhone())
                .build();
    }
}
