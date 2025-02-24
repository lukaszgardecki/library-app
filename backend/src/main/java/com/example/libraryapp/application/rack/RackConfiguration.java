package com.example.libraryapp.application.rack;

import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RackConfiguration {

    @Bean
    RackFacade rackFacade(
            RackRepositoryPort rackRepository,
            BookItemFacade bookItemFacade
    ) {
        RackService rackService = new RackService(rackRepository, bookItemFacade);
        return new RackFacade(
                new GetRackUseCase(rackRepository),
                new AddRackUseCase(rackRepository),
                new DeleteRackUseCase(rackService)
        );
    }
}
