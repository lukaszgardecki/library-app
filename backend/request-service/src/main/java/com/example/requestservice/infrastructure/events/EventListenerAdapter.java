package com.example.requestservice.infrastructure.events;

import com.example.requestservice.domain.event.incoming.BookItemDeletedEvent;
import com.example.requestservice.domain.event.incoming.BookItemLoanedEvent;
import com.example.requestservice.domain.event.incoming.BookItemLostEvent;
import com.example.requestservice.domain.event.incoming.BookItemReturnedEvent;
import com.example.requestservice.domain.ports.EventListenerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventListenerAdapter {
    private final EventListenerPort eventListener;

    private static final String BOOK_ITEM_LOANED_SUCCESS_TOPIC = "book-item.loaned.success";
    private static final String BOOK_ITEM_RETURNED_SUCCESS_TOPIC = "book-item.returned.success";
    private static final String BOOK_ITEM_LOST_TOPIC = "book-item.lost";
    private static final String BOOK_ITEM_DELETED_TOPIC = "book-item.deleted";

    @KafkaListener(topics = BOOK_ITEM_LOANED_SUCCESS_TOPIC, groupId = "request-service-listeners")
    void bookItemLoaned(BookItemLoanedEvent event) {
        eventListener.handleBookItemLoanedEvent(event.getRequestId());
    }

    @KafkaListener(topics = BOOK_ITEM_RETURNED_SUCCESS_TOPIC, groupId = "request-service-listeners")
    void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.handleBookItemReturnedEvent(event.getBookItemId());
    }

    @KafkaListener(topics = BOOK_ITEM_LOST_TOPIC, groupId = "request-service-listeners")
    void bookItemReturned(BookItemLostEvent event) {
        eventListener.handleBookItemLostEvent(event.getBookItemId());
    }

    @KafkaListener(topics = BOOK_ITEM_DELETED_TOPIC, groupId = "request-service-listeners")
    void bookItemDeleted(BookItemDeletedEvent event) {
        eventListener.handleBookItemDeletedEvent(event.getBookItemId());
    }
}
