package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import com.example.libraryapp.domain.shelf.ports.ShelfRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
class WarehouseConfiguration {

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
                bookFacade, bookItemFacade, bookItemRequestFacade, userFacade, personFacade, rackService
        );
        return new WarehouseFacade(
                new GetBookItemRequestList(bookItemRequestService),
                new GetAllRacksUseCase(rackRepository),
                new GetAllShelvesUseCase(shelfRepository),
                new GetRackUseCase(rackService),
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
            RackRepositoryPort rackRepository
    ) {
        RackService rackService = new RackService(rackRepository, bookItemFacade);

        BookItemRequestService bookItemRequestService = new BookItemRequestService(
                bookFacade, bookItemFacade, bookItemRequestFacade, userFacade, personFacade, rackService
        );
        return new WarehouseEventListenerAdapter(messagingTemplate, bookItemRequestService);
    }
}
