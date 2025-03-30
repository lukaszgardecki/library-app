package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.bookitemrequest.BookItemRequestFacade;
import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.application.rack.RackFacade;
import com.example.libraryapp.application.user.UserFacade;
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
            RackFacade rackFacade
    ) {
        WarehouseService warehouseService = new WarehouseService(
                bookFacade, bookItemFacade, bookItemRequestFacade, userFacade, personFacade, rackFacade
        );
        return new WarehouseFacade(
                new GetBookItemRequestList(warehouseService)
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
            RackFacade rackFacade
    ) {
        WarehouseService warehouseService = new WarehouseService(
                bookFacade, bookItemFacade, bookItemRequestFacade, userFacade, personFacade, rackFacade
        );
        return new WarehouseEventListenerAdapter(messagingTemplate, warehouseService);
    }
}
