package com.example.notificationservice.infrastructure.events;

import com.example.notificationservice.domain.event.incoming.*;
import com.example.notificationservice.domain.ports.in.EventListenerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventListenerAdapter {
    private final EventListenerPort eventListener;

    @KafkaListener(topics = "auth-service.user.created", groupId = "notification-service.user.created.consumers")
    void userCreated(UserCreatedEvent event) {
        eventListener.handleUserCreatedEvent(event);
    }

    @KafkaListener(topics = "request-service.request.created", groupId = "notification-service.request.created.consumers")
    void requestCreated(RequestCreatedEvent event) {
        eventListener.handleRequestCreatedEvent(event);
    }

    @KafkaListener(topics = "request-service.request.ready", groupId = "notification-service.request.ready.consumers")
    void requestReady(RequestReadyEvent event) {
        eventListener.handleRequestReadyEvent(event);
    }

    @KafkaListener(topics = "request-service.request.canceled", groupId = "notification-service.request.canceled.consumers")
    void requestCanceled(RequestCanceledEvent event) {
        eventListener.handleRequestCanceledEvent(event);
    }

    @KafkaListener(topics = "request-service.reservation.created", groupId = "notification-service.reservation.created.consumers")
    void reservationCreated(ReservationCreatedEvent event) {
        eventListener.handleReservationCreatedEvent(event);
    }

    @KafkaListener(topics = "request-service.request.available-to-loan", groupId = "notification-service.request.available-to-loan.consumers")
    void requestAvailableToLoan(RequestAvailableToLoanEvent event) {
        eventListener.handleRequestAvailableToLoanEvent(event);
    }

    @KafkaListener(topics = "loan-service.loan.created", groupId = "notification-service.loan.created.consumers")
    void loanCreated(LoanCreatedEvent event) {
        eventListener.handleLoanCreatedEvent(event);
    }

    @KafkaListener(topics = "loan-service.loan.prolonged", groupId = "notification-service.loan.prolonged.consumers")
    void loanProlonged(LoanProlongedEvent event) {
        eventListener.handleLoanProlongedEvent(event);
    }

    @KafkaListener(topics = "loan-service.loan.prolongation.not-allowed", groupId = "notification-service.loan.prolongation.not-allowed.consumers")
    void loanProlongationNotAllowed(LoanProlongationNotAllowed event) {
        eventListener.handleLoanProlongationNotAllowedEvent(event);
    }

    @KafkaListener(topics = "loan-service.book-item.returned", groupId = "notification-service.book-item.returned.consumers")
    void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.handleBookItemReturnedEvent(event);
    }

    @KafkaListener(topics = "loan-service.book-item.lost", groupId = "notification-service.book-item.lost.consumers")
    void bookItemLost(BookItemLostEvent event) {
        eventListener.handleBookItemLostEvent(event);
    }
}
