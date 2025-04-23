package com.example.userservice.domain.dto.person;

import com.example.userservice.domain.model.person.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PersonDto{
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String pesel;
    private LocalDate dateOfBirth;
    private String nationality;
    private String fathersName;
    private String mothersName;
    private AddressDto address;
    private String phone;
}
