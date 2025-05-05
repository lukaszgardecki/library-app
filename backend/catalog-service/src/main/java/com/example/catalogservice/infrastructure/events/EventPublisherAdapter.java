package com.example.catalogservice.infrastructure.events;

import com.example.catalogservice.domain.event.outgoing.BookItemDeletedEvent;
import com.example.catalogservice.domain.model.book.BookId;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String BOOK_ITEM_DELETED_TOPIC = "catalog-service.book-item.deleted";

    @Override
    public void publishBookItemDeletedEvent(BookItemId bookItemId, BookId bookId) {
        template.send(BOOK_ITEM_DELETED_TOPIC, new BookItemDeletedEvent(bookItemId, bookId));
    }
}
