package com.example.libraryapp.application.person;

import com.example.libraryapp.domain.person.model.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageByQueryUseCase {
    private final PersonService personService;

    Page<Person> execute(String query, Pageable pageable) {
        return personService.getAllByQuery(query, pageable);
    }
}
