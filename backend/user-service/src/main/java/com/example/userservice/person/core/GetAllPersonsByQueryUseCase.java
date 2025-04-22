package com.example.userservice.person.core;

import com.example.userservice.person.domain.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
class GetAllPersonsByQueryUseCase {
    private final PersonService personService;

    Page<Person> execute(String query, Pageable pageable) {
        return personService.getAllByQuery(query, pageable);
    }

    List<Person> execute(String query) {
        return personService.getAllByQuery(query);
    }
}
