package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.core.book.BookConfiguration;
import com.example.libraryapp.core.book.BookFacade;
import com.example.libraryapp.core.bookitem.BookItemConfiguration;
import com.example.libraryapp.core.bookitem.BookItemFacade;
import com.example.libraryapp.core.bookitemrequest.BookItemRequestConfiguration;
import com.example.libraryapp.core.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.core.person.PersonConfiguration;
import com.example.libraryapp.core.person.PersonFacade;
import com.example.libraryapp.core.user.UserConfiguration;
import com.example.libraryapp.core.user.UserFacade;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import com.example.libraryapp.domain.shelf.ports.ShelfRepositoryPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryRackRepositoryAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryShelfRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
public class WarehouseConfiguration {

    public WarehouseFacade warehouseFacade() {
        InMemoryRackRepositoryAdapter rackRepository = new InMemoryRackRepositoryAdapter();
        InMemoryShelfRepositoryAdapter shelfRepository = new InMemoryShelfRepositoryAdapter();
        BookFacade bookFacade = new BookConfiguration().bookFacade();
        BookItemFacade bookItemFacade = new BookItemConfiguration().bookItemFacade();
        BookItemRequestFacade bookItemRequestFacade = new BookItemRequestConfiguration().bookItemRequestFacade();
        UserFacade userFacade = new UserConfiguration().userFacade();
        PersonFacade personFacade = new PersonConfiguration().personFacade();
        RackService rackService = new RackService(rackRepository, bookItemFacade);
        ShelfService shelfService = new ShelfService(shelfRepository, bookItemFacade);
        BookItemRequestService bookItemRequestService = new BookItemRequestService(
                bookFacade, bookItemFacade, bookItemRequestFacade, userFacade, personFacade, rackService, shelfService
        );
        return new WarehouseFacade(
                new GetBookItemRequestList(bookItemRequestService),
                new GetAllRacksUseCase(rackRepository),
                new GetAllShelvesUseCase(shelfRepository),
                new GetRackUseCase(rackService),
                new GetShelfUseCase(shelfService),
                new AddRackUseCase(rackService),
                new AddShelfUseCase(shelfService),
                new UpdateRackUseCase(rackService),
                new UpdateShelfUseCase(shelfService),
                new DeleteRackUseCase(rackService),
                new DeleteShelfUseCase(shelfService)
        );
    }

    @Bean
    WarehouseFacade warehouseFacade(
            BookFacade bookFacade,
            BookItemFacade bookItemFacade,
            BookItemRequestFacade bookItemRequestFacade,
            UserFacade userFacade,
            PersonFacade personFacade,
            RackRepositoryPort rackRepository,
            ShelfRepositoryPort shelfRepository
    ) {
        RackService rackService = new RackService(rackRepository, bookItemFacade);
        ShelfService shelfService = new ShelfService(shelfRepository, bookItemFacade);
        BookItemRequestService bookItemRequestService = new BookItemRequestService(
                bookFacade, bookItemFacade, bookItemRequestFacade, userFacade, personFacade, rackService, shelfService
        );
        return new WarehouseFacade(
                new GetBookItemRequestList(bookItemRequestService),
                new GetAllRacksUseCase(rackRepository),
                new GetAllShelvesUseCase(shelfRepository),
                new GetRackUseCase(rackService),
                new GetShelfUseCase(shelfService),
                new AddRackUseCase(rackService),
                new AddShelfUseCase(shelfService),
                new UpdateRackUseCase(rackService),
                new UpdateShelfUseCase(shelfService),
                new DeleteRackUseCase(rackService),
                new DeleteShelfUseCase(shelfService)
        );
    }

    @Bean
    WarehouseEventListenerAdapter warehouseEventListener(
            SimpMessagingTemplate messagingTemplate,
            BookFacade bookFacade,
            BookItemFacade bookItemFacade,
            BookItemRequestFacade bookItemRequestFacade,
            UserFacade userFacade,
            PersonFacade personFacade,
            RackRepositoryPort rackRepository,
            ShelfRepositoryPort shelfRepository
    ) {
        RackService rackService = new RackService(rackRepository, bookItemFacade);
        ShelfService shelfService = new ShelfService(shelfRepository, bookItemFacade);
        BookItemRequestService bookItemRequestService = new BookItemRequestService(
                bookFacade, bookItemFacade, bookItemRequestFacade, userFacade, personFacade, rackService, shelfService
        );
        return new WarehouseEventListenerAdapter(messagingTemplate, bookItemRequestService);
    }
}
