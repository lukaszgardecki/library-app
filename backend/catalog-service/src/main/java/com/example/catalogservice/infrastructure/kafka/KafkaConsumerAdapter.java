package com.example.catalogservice.infrastructure.kafka;

import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.model.bookitem.values.LoanCreationDate;
import com.example.catalogservice.domain.model.bookitem.values.LoanDueDate;
import com.example.catalogservice.domain.ports.in.EventListenerPort;
import com.example.catalogservice.infrastructure.kafka.event.incoming.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaConsumerAdapter {
    private final EventListenerPort eventListener;

    private static final String REQUEST_CREATED_TOPIC = "request-service.request.created";
    private static final String REQUEST_CANCELED_TOPIC = "request-service.request.canceled";
    private static final String LOAN_CREATED_TOPIC = "loan-service.loan.created";
    private static final String LOAN_PROLONGED_TOPIC = "loan-service.loan.prolonged";
    private static final String BOOK_ITEM_RETURNED_TOPIC = "loan-service.book-item.returned";
    private static final String BOOK_ITEM_LOST_TOPIC = "loan-service.book-item.lost";

    @KafkaListener(topics = REQUEST_CREATED_TOPIC, groupId = "catalog-service.request.created.consumers")
    void requestCreated(RequestCreatedEvent event) {
        eventListener.handleRequestCreatedEvent(new BookItemId(event.getBookItemId()));
    }

    @KafkaListener(topics = REQUEST_CANCELED_TOPIC, groupId = "catalog-service.request.canceled.consumers")
    void requestCanceled(RequestCanceledEvent event) {
        eventListener.handleRequestCanceledEvent(new BookItemId(event.getBookItemId()));
    }

    @KafkaListener(topics = LOAN_CREATED_TOPIC, groupId = "catalog-service.loan.created.consumers")
    void loanCreated(LoanCreatedEvent event) {
        eventListener.handleLoanCreatedEvent(
                new BookItemId(event.getBookItemId()),
                new LoanCreationDate(event.getLoanCreationDate()),
                new LoanDueDate(event.getLoanDueDate())
        );
    }

    @KafkaListener(topics = LOAN_PROLONGED_TOPIC, groupId = "catalog-service.loan.prolonged.consumers")
    void loanProlonged(LoanProlongedEvent event) {
        eventListener.handleLoanProlongedEvent(
                new BookItemId(event.getBookItemId()),
                new LoanDueDate(event.getLoanDueDate())
        );
    }

    @KafkaListener(topics = BOOK_ITEM_RETURNED_TOPIC, groupId = "catalog-service.book-item.returned.consumers")
    void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.handleBookItemReturnedEvent(
                new BookItemId(event.getBookItemId()),
                new LoanDueDate(event.getLoanReturnDate().toLocalDate())
        );
    }

    @KafkaListener(topics = BOOK_ITEM_LOST_TOPIC, groupId = "catalog-service.book-item.lost.consumers")
    void bookItemLost(BookItemLostEvent event) {
        eventListener.handleBookItemLostEvent(new BookItemId(event.getBookItemId()));
    }
}
