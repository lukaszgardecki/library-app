package com.example.catalogservice.bookitem.infrastructure.events;

import com.example.catalogservice.bookitem.domain.event.incoming.*;
import com.example.catalogservice.bookitem.domain.ports.EventListenerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventListenerAdapter {
    private final EventListenerPort eventListener;

    @KafkaListener(topics = "book-item-requested", groupId = "catalog-service-listeners")
    void bookItemRequested(BookItemRequestedEvent event) {
        eventListener.updateBookItemOnRequest(event.getBookItemId());
    }

    @KafkaListener(topics = "book-item-loaned", groupId = "catalog-service-listeners")
    void bookItemLoaned(BookItemLoanedEvent event) {
        eventListener.updateBookItemOnLoan(event.getBookItemId(), event.getCreationDate(), event.getDueDate());
    }

    @KafkaListener(topics = "book-item-returned", groupId = "catalog-service-listeners")
    void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.updateBookItemOnReturn(event.getBookItemId(), event.getDueDate());
    }

    @KafkaListener(topics = "book-item-lost", groupId = "catalog-service-listeners")
    void bookItemLost(BookItemLostEvent event) {
        eventListener.updateBookItemOnLoss(event.getBookItemId());
    }

    @KafkaListener(topics = "book-item-request-canceled", groupId = "catalog-service-listeners")
    void bookItemRequestCanceled(BookItemRequestCanceledEvent event) {
        eventListener.updateBookItemOnRequestCancellation(event.getBookItemId());
    }
}
