package com.example.libraryapp.NEWapplication.rack;

import com.example.libraryapp.NEWdomain.rack.ports.RackRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RackConfiguration {


    @Bean
    RackFacade rackFacade(RackRepository rackRepository) {
        return new RackFacade(
                new GetRackUseCase(rackRepository),
                new AddRackUseCase(rackRepository),
                new DeleteRackUseCase(rackRepository)
        );
    }
}
