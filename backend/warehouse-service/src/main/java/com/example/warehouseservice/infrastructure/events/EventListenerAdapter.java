package com.example.warehouseservice.infrastructure.events;

import com.example.warehouseservice.core.bookitemrequest.BookItemRequestFacade;
import com.example.warehouseservice.domain.dto.WarehouseBookItemRequestListViewDto;
import com.example.warehouseservice.domain.event.incoming.RequestCreatedEvent;
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

    private static final String REQUEST_CREATED_TOPIC = "request-service.request.created";

    @KafkaListener(topics = REQUEST_CREATED_TOPIC, groupId = "warehouse-service.request.created.consumers")
    void requestCreated(RequestCreatedEvent event) {
        WarehouseBookItemRequestListViewDto bookItemRequest = bookItemRequestFacade.getBookItemRequestById(event.getRequestId());
        messagingTemplate.convertAndSend(
                "/queue/warehouse/pending",
                bookItemRequest
        );
    }
}
