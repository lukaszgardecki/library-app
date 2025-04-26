package com.example.warehouseservice.infrastructure.events;

import com.example.warehouseservice.domain.event.outgoing.RequestReadyEvent;
import com.example.warehouseservice.domain.model.BookItemId;
import com.example.warehouseservice.domain.model.RequestId;
import com.example.warehouseservice.domain.model.UserId;
import com.example.warehouseservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String REQUEST_READY_TOPIC = "book-item-request.ready";

    @Override
    public void publishBookItemRequestReadyEvent(UserId userId, BookItemId bookItemId, RequestId requestId) {
        template.send(REQUEST_READY_TOPIC, new RequestReadyEvent(userId, bookItemId, requestId));
    }
}
