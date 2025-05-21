package com.example.authservice.domain.model.user;

import com.example.authservice.domain.model.authdetails.values.Email;
import com.example.authservice.domain.model.authdetails.values.Password;
import com.example.authservice.domain.model.person.Person;

public record RegisterToSave(
        Email username,
        Password password,
        Person person
) { }
