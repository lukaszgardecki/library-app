package com.example.userservice.infrastructure.kafka;

import com.example.userservice.domain.integration.fine.FineAmount;
import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.ports.in.EventListenerPort;
import com.example.userservice.infrastructure.kafka.event.incoming.*;
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
    private static final String FINE_PAID_TOPIC = "fine-service.fine.paid";

    @KafkaListener(topics = REQUEST_CREATED_TOPIC, groupId = "user-service.request.created.consumers")
    void requestCreated(RequestCreatedEvent event) {
        eventListener.handleRequestCreatedEvent(new UserId(event.getUserId()));
    }

    @KafkaListener(topics = REQUEST_CANCELED_TOPIC, groupId = "user-service.request.canceled.consumers")
    void requestCanceled(RequestCanceledEvent event) {
        eventListener.handleRequestCanceledEvent(new UserId(event.getUserId()));
    }

    @KafkaListener(topics = LOAN_CREATED_TOPIC, groupId = "user-service.loan.created.consumers")
    void loanCreated(LoanCreatedEvent event) {
        eventListener.handleLoanCreatedEvent(new UserId(event.getUserId()));
    }

    @KafkaListener(topics = LOAN_PROLONGED_TOPIC, groupId = "user-service.loan.prolonged.consumers")
    void loanProlonged(LoanProlongedEvent event) {
        eventListener.handleLoanProlongedEvent(new UserId(event.getUserId()));
    }

    @KafkaListener(topics = BOOK_ITEM_RETURNED_TOPIC, groupId = "user-service.book-item.returned.consumers")
    void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.handleBookItemReturnedEvent(new UserId(event.getUserId()));
    }

    @KafkaListener(topics = BOOK_ITEM_LOST_TOPIC, groupId = "user-service.book-item.lost.consumers")
    void bookItemLost(BookItemLostEvent event) {
        eventListener.handleBookItemLostEvent(new UserId(event.getUserId()));
    }

    @KafkaListener(topics = FINE_PAID_TOPIC, groupId = "user-service.fine.paid.consumers")
    void finePaid(FinePaidEvent event) {
        eventListener.handleFinePaidEvent(new UserId(event.getUserId()), new FineAmount(event.getFineAmount()));
    }
}
