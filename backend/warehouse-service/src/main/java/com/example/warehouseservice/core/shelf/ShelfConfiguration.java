package com.example.warehouseservice.core.shelf;

import com.example.warehouseservice.domain.ports.out.CatalogServicePort;
import com.example.warehouseservice.domain.ports.out.ShelfRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShelfConfiguration {

    @Bean
    ShelfFacade shelfFacade(
            ShelfRepositoryPort shelfRepository,
            CatalogServicePort bookItemService
    ) {
        ShelfService shelfService = new ShelfService(shelfRepository, bookItemService);
        return new ShelfFacade(
                new GetAllShelvesUseCase(shelfService),
                new GetShelfUseCase(shelfService),
                new AddShelfUseCase(shelfService),
                new UpdateShelfUseCase(shelfService),
                new DeleteShelfUseCase(shelfService)
        );
    }
}
