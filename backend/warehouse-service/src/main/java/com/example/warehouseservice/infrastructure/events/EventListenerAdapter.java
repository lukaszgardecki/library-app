package com.example.warehouseservice.infrastructure.events;

import com.example.warehouseservice.core.bookitemrequest.BookItemRequestFacade;
import com.example.warehouseservice.domain.dto.WarehouseBookItemRequestListViewDto;
import com.example.warehouseservice.domain.event.incoming.BookItemRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventListenerAdapter {
//    private final EventListenerPort eventListener;
    private final SimpMessagingTemplate messagingTemplate;
    private final BookItemRequestFacade bookItemRequestFacade;

    @KafkaListener(topics = "book-item-requested", groupId = "catalog-service-listeners")
    void bookItemRequested(BookItemRequestedEvent event) {
        WarehouseBookItemRequestListViewDto bookItemRequest = bookItemRequestFacade.getBookItemRequest(event.getBookItemRequest());
        messagingTemplate.convertAndSend(
                "/queue/warehouse/pending",
                bookItemRequest
        );
    }
}
