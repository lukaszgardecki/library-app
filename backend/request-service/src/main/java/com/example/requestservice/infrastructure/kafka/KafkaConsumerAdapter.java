package com.example.requestservice.infrastructure.kafka;

import com.example.requestservice.domain.integration.catalog.BookId;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.domain.ports.in.EventListenerPort;
import com.example.requestservice.infrastructure.kafka.event.incoming.BookItemDeletedEvent;
import com.example.requestservice.infrastructure.kafka.event.incoming.BookItemLostEvent;
import com.example.requestservice.infrastructure.kafka.event.incoming.BookItemReturnedEvent;
import com.example.requestservice.infrastructure.kafka.event.incoming.LoanCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaConsumerAdapter {
    private final EventListenerPort eventListener;

    private static final String BOOK_ITEM_DELETED_TOPIC = "catalog-service.book-item.deleted";
    private static final String LOAN_CREATED_TOPIC = "loan-service.loan.created";
    private static final String BOOK_ITEM_RETURNED_TOPIC = "loan-service.book-item.returned";
    private static final String BOOK_ITEM_LOST_TOPIC = "loan-service.book-item.lost";

    @KafkaListener(topics = BOOK_ITEM_DELETED_TOPIC, groupId = "request-service.book-item.deleted.consumers")
    void bookItemDeleted(BookItemDeletedEvent event) {
        eventListener.handleBookItemDeletedEvent(
                new BookItemId(event.getBookItemId()),
                new BookId(event.getBookId())
        );
    }

    @KafkaListener(topics = LOAN_CREATED_TOPIC, groupId = "request-service.loan.created.consumers")
    void bookItemLoanCreated(LoanCreatedEvent event) {
        eventListener.handleLoanCreatedEvent(new RequestId(event.getRequestId()));
    }

    @KafkaListener(topics = BOOK_ITEM_RETURNED_TOPIC, groupId = "request-service.book-item.returned.consumers")
    void bookItemLost(BookItemReturnedEvent event) {
        eventListener.handleBookItemReturnedEvent(new BookItemId(event.getBookItemId()));
    }

    @KafkaListener(topics = BOOK_ITEM_LOST_TOPIC, groupId = "request-service.book-item.lost.consumers")
    void bookItemLost(BookItemLostEvent event) {
        eventListener.handleBookItemLostEvent(
                new BookItemId(event.getBookItemId()),
                new BookId(event.getBookId())
        );
    }
}
