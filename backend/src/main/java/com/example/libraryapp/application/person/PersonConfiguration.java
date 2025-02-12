package com.example.libraryapp.application.person;

import com.example.libraryapp.domain.person.ports.PersonRepository;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryPersonRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfiguration {

    public PersonFacade personFacade() {
        InMemoryPersonRepositoryAdapter personRepository = new InMemoryPersonRepositoryAdapter();
        PersonService personService = new PersonService(personRepository);
        return new PersonFacade(
                new GetPageByQueryUseCase(personService),
                new GetPersonUseCase(personRepository),
                new SavePersonUseCase(personRepository),
                new DeletePersonUseCase(personRepository),
                new CountByAgeBetweenUseCase(personService),
                new GetCitiesByUserCountDescUseCase(personService)
        );
    }

    @Bean
    public PersonFacade personFacade(
            PersonRepository personRepository
    ) {
        PersonService personService = new PersonService(personRepository);
        return new PersonFacade(
                new GetPageByQueryUseCase(personService),
                new GetPersonUseCase(personRepository),
                new SavePersonUseCase(personRepository),
                new DeletePersonUseCase(personRepository),
                new CountByAgeBetweenUseCase(personService),
                new GetCitiesByUserCountDescUseCase(personService)
        );
    }
}
