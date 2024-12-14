package com.example.libraryapp.NEWapplication;

import com.example.libraryapp.NEWdomain.user.model.AccountStatus;
import com.example.libraryapp.NEWdomain.user.dto.RegisterUserDto;
import com.example.libraryapp.NEWdomain.user.model.Role;
import com.example.libraryapp.NEWdomain.user.dto.UserDto;
import com.example.libraryapp.NEWdomain.user.model.User;
import com.example.libraryapp.NEWdomain.user.model.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

class TestHelper {

    RegisterUserDto createNewUser() {
        RegisterUserDto user = new RegisterUserDto();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("j.doe@gmail.com");
        user.setPassword("password");
        user.setPesel("12345678901");
        user.setDateOfBirth(LocalDate.of(1990, 1, 1));
        user.setGender(Gender.MALE);
        user.setNationality("Polish");
        user.setMothersName("Jane Doe");
        user.setFathersName("John Doe");
        user.setStreetAddress("Main Street 1");
        user.setZipCode("00-000");
        user.setCity("Warsaw");
        user.setState("Mazovia");
        user.setCountry("Poland");
        user.setPhone("123456789");
        return user;
    }

    UserDto getUserDto() {
        return UserDto.builder()
                .id(1L)
                .registrationDate(LocalDateTime.of(
                        LocalDate.of(1990, 1, 1),
                        LocalTime.of(12, 0)
                ))
                .password("password")
                .email("test@email.com")
                .status(AccountStatus.ACTIVE)
                .role(Role.USER)
                .totalBooksBorrowed(4)
                .totalBooksReserved(2)
                .charge(null)
                .cardId(1L)
                .personId(1L)
                .build();
    }

    User getUser() {
        return User.builder()
                .id(1L)
                .registrationDate(LocalDateTime.of(
                        LocalDate.of(1990, 1, 1),
                        LocalTime.of(12, 0)
                ))
                .password("password")
                .email("test@email.com")
                .status(AccountStatus.ACTIVE)
                .role(Role.USER)
                .totalBooksBorrowed(4)
                .totalBooksRequested(2)
                .charge(null)
                .cardId(1L)
                .personId(1L)
                .build();
    }
}
