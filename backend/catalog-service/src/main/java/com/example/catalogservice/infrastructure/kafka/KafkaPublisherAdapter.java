package com.example.catalogservice.infrastructure.kafka;

import com.example.catalogservice.domain.model.book.values.BookId;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.ports.out.EventPublisherPort;
import com.example.catalogservice.infrastructure.kafka.event.outgoing.BookItemDeletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String BOOK_ITEM_DELETED_TOPIC = "catalog-service.book-item.deleted";

    @Override
    public void publishBookItemDeletedEvent(BookItemId bookItemId, BookId bookId) {
        template.send(BOOK_ITEM_DELETED_TOPIC, new BookItemDeletedEvent(bookItemId.value(), bookId.value()));
    }
}
