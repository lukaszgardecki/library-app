package com.example.userservice.user.infrastructure.events;

import com.example.userservice.user.domain.event.incoming.*;
import com.example.userservice.user.domain.ports.EventListenerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventListenerAdapter {
    private final EventListenerPort eventListener;

    @KafkaListener(topics = "book-item-requested", groupId = "user-service-listeners")
    void bookItemRequested(BookItemRequestedEvent event) {
        eventListener.updateUserOnRequest(event.getUserId());
    }

    @KafkaListener(topics = "book-item-loaned", groupId = "user-service-listeners")
    void bookItemLoaned(BookItemLoanedEvent event) {
        eventListener.updateUserOnLoan(event.getUserId());
    }

    @KafkaListener(topics = "book-item-returned", groupId = "user-service-listeners")
    void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.updateUserOnReturn(event.getUserId());
    }

    @KafkaListener(topics = "book-item-lost", groupId = "user-service-listeners")
    void bookItemLost(BookItemLostEvent event) {
        eventListener.updateUserOnLoss(event.getUserId());
    }

    @KafkaListener(topics = "book-item-request-canceled", groupId = "user-service-listeners")
    void bookItemRequestCanceled(BookItemRequestCanceledEvent event) {
        eventListener.updateUserOnRequestCancellation(event.getUserId());
    }

    @KafkaListener(topics = "book-item-request-renewed", groupId = "user-service-listeners")
    void bookItemRequestRenewed(BookItemRenewedEvent event) {
        eventListener.updateUserOnRenewal(event.getUserId());
    }

    @KafkaListener(topics = "fine-paid", groupId = "user-service-listeners")
    void finePaid(FinePaidEvent event) {
        eventListener.updateUserOnFinePaid(event.getUserId(), event.getFineAmount());
    }
}
