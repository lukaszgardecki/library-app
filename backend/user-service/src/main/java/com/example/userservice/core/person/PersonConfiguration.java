package com.example.userservice.core.person;

import com.example.userservice.domain.ports.PersonRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfiguration {

    @Bean
    public PersonFacade personFacade(
            PersonRepositoryPort personRepository
    ) {
        PersonService personService = new PersonService(personRepository);
        return new PersonFacade(
                new GetAllPersonsByQueryUseCase(personService),
                new GetPersonUseCase(personRepository),
                new SavePersonUseCase(personRepository),
                new DeletePersonUseCase(personRepository),
                new CountByAgeBetweenUseCase(personService),
                new GetCitiesByUserCountDescUseCase(personService)
        );
    }
}
