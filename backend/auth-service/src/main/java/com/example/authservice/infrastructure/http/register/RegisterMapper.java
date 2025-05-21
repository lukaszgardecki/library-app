package com.example.authservice.infrastructure.http.register;

import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.person.Person;
import com.example.authservice.domain.model.person.values.*;
import com.example.authservice.domain.model.user.RegisterToSave;
import com.example.authservice.infrastructure.http.register.dto.RegisterToSaveDto;

class RegisterMapper {

    static RegisterToSave toModel(RegisterToSaveDto dto) {
        return new RegisterToSave(
                new Email(dto.username()),
                new Password(dto.password()),
                new Person(
                        null,
                        new PersonFirstName(dto.firstName()),
                        new PersonLastName(dto.lastName()),
                        Gender.valueOf(dto.gender()),
                        new Pesel(dto.pesel()),
                        new BirthDate(dto.dateOfBirth()),
                        new Nationality(dto.nationality()),
                        new FatherName(dto.fathersName()),
                        new MotherName(dto.mothersName()),
                        new Address(
                                new StreetAddress(dto.streetAddress()),
                                new City(dto.city()),
                                new State(dto.state()),
                                new ZipCode(dto.zipCode()),
                                new Country(dto.country())
                        ),
                        new PhoneNumber(dto.phone())
                )
        );
    }
}
