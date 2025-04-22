package com.example.catalogservice.bookitem.infrastructure.events;

import com.example.catalogservice.book.domain.model.Title;
import com.example.catalogservice.bookitem.domain.event.outgoing.BookItemDeletedEvent;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.ports.EventPublisherPort;
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
