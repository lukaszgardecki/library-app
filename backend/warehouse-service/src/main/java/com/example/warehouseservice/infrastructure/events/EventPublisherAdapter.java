package com.example.warehouseservice.infrastructure.events;

import com.example.warehouseservice.domain.model.RequestId;
import com.example.warehouseservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String BOOK_ITEM_REQUEST_READY_TOPIC = "book-item-request.ready";

    @Override
    public void publishBookItemRequestReadyEvent(RequestId requestId) {
        template.send(BOOK_ITEM_REQUEST_READY_TOPIC, requestId);
    }
}
