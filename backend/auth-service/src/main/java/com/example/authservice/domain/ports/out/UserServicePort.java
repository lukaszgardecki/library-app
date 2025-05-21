package com.example.authservice.domain.ports.out;

import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.model.person.Person;

public interface UserServicePort {

    UserId register(Person userData);

    Person getPersonByUser(Long userId);
}
