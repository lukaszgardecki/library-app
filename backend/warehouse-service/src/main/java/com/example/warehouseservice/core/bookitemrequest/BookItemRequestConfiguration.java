package com.example.warehouseservice.core.bookitemrequest;

import com.example.warehouseservice.core.rack.RackFacade;
import com.example.warehouseservice.core.shelf.ShelfFacade;
import com.example.warehouseservice.domain.ports.BookItemRequestServicePort;
import com.example.warehouseservice.domain.ports.CatalogServicePort;
import com.example.warehouseservice.domain.ports.EventPublisherPort;
import com.example.warehouseservice.domain.ports.UserServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BookItemRequestConfiguration {

    @Bean
    BookItemRequestFacade bookItemRequestFacade(
            CatalogServicePort catalogService,
            UserServicePort userService,
            BookItemRequestServicePort bookItemRequestService,
            RackFacade rackFacade,
            ShelfFacade shelfFacade
    ) {
        BookItemRequestService requestService = new BookItemRequestService(
                catalogService, userService, bookItemRequestService, rackFacade, shelfFacade
        );
        return new BookItemRequestFacade(
                new GetBookItemRequestList(requestService),
                new GetBookItemRequest(requestService),
                new ChangeBookItemRequestStatusToReadyUseCase(bookItemRequestService)
        );
    }
}
