package com.example.catalogservice.infrastructure.events;

import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.event.outgoing.BookItemDeletedEvent;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String BOOK_ITEM_DELETED_SUCCESS_TOPIC = "book-item.deleted.success";

    @Override
    public void publishBookItemDeletedEvent(BookItemId bookItemId, Title title) {
        template.send(BOOK_ITEM_DELETED_SUCCESS_TOPIC, new BookItemDeletedEvent(bookItemId, title));
    }
}
