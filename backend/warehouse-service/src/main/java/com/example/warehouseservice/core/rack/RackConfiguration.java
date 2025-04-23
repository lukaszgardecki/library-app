package com.example.warehouseservice.core.rack;

import com.example.warehouseservice.domain.ports.CatalogServicePort;
import com.example.warehouseservice.domain.ports.RackRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RackConfiguration {

    @Bean
    RackFacade rackFacade(
            RackRepositoryPort rackRepository,
            CatalogServicePort bookItemService
    ) {
        RackService rackService = new RackService(rackRepository, bookItemService);
        return new RackFacade(
                new GetAllRacksUseCase(rackService),
                new GetRackUseCase(rackService),
                new AddRackUseCase(rackService),
                new UpdateRackUseCase(rackService),
                new DeleteRackUseCase(rackService)
        );
    }
}
