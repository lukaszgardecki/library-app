package com.example.notificationservice.infrastructure.kafka;

import com.example.notificationservice.domain.ports.in.EventListenerPort;
import com.example.notificationservice.infrastructure.kafka.event.incoming.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaConsumerAdapter {
    private final EventListenerPort eventListener;

    @KafkaListener(topics = "auth-service.user.created", groupId = "notification-service.user.created.consumers")
    void userCreated(UserCreatedEvent event) {
        eventListener.handleUserCreatedEvent(event.getUserId(), event.getFirstName());
    }

    @KafkaListener(topics = "request-service.request.created", groupId = "notification-service.request.created.consumers")
    void requestCreated(RequestCreatedEvent event) {
        eventListener.handleRequestCreatedEvent(event.getUserId(), event.getBookTitle());
    }

    @KafkaListener(topics = "request-service.request.ready", groupId = "notification-service.request.ready.consumers")
    void requestReady(RequestReadyEvent event) {
        eventListener.handleRequestReadyEvent(event.getUserId(), event.getBookTitle());
    }

    @KafkaListener(topics = "request-service.request.canceled", groupId = "notification-service.request.canceled.consumers")
    void requestCanceled(RequestCanceledEvent event) {
        eventListener.handleRequestCanceledEvent(event.getUserId(), event.getBookTitle(), event.getBookItemId());
    }

    @KafkaListener(topics = "request-service.reservation.created", groupId = "notification-service.reservation.created.consumers")
    void reservationCreated(ReservationCreatedEvent event) {
        eventListener.handleReservationCreatedEvent(event.getUserId(), event.getBookTitle(), event.getQueue(), event.getLoanDueDate());
    }

    @KafkaListener(topics = "request-service.request.available-to-loan", groupId = "notification-service.request.available-to-loan.consumers")
    void requestAvailableToLoan(RequestAvailableToLoanEvent event) {
        eventListener.handleRequestAvailableToLoanEvent(event.getUserId(), event.getBookTitle());
    }

    @KafkaListener(topics = "loan-service.loan.created", groupId = "notification-service.loan.created.consumers")
    void loanCreated(LoanCreatedEvent event) {
        eventListener.handleLoanCreatedEvent(event.getUserId(), event.getBookTitle());
    }

    @KafkaListener(topics = "loan-service.loan.prolonged", groupId = "notification-service.loan.prolonged.consumers")
    void loanProlonged(LoanProlongedEvent event) {
        eventListener.handleLoanProlongedEvent(event.getUserId(), event.getBookTitle(), event.getLoanDueDate());
    }

    @KafkaListener(topics = "loan-service.loan.prolongation.not-allowed", groupId = "notification-service.loan.prolongation.not-allowed.consumers")
    void loanProlongationNotAllowed(LoanProlongationNotAllowed event) {
        eventListener.handleLoanProlongationNotAllowedEvent(event.getUserId(), event.getBookTitle());
    }

    @KafkaListener(topics = "loan-service.book-item.returned", groupId = "notification-service.book-item.returned.consumers")
    void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.handleBookItemReturnedEvent(event.getUserId(), event.getBookTitle());
    }

    @KafkaListener(topics = "loan-service.book-item.lost", groupId = "notification-service.book-item.lost.consumers")
    void bookItemLost(BookItemLostEvent event) {
        eventListener.handleBookItemLostEvent(event.getUserId(), event.getBookTitle(), event.getCharge());
    }
}
