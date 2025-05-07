package com.example.activityservice.infrastructure.events;

import com.example.activityservice.domain.event.incoming.*;
import com.example.activityservice.domain.ports.in.EventListenerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventListenerAdapter {
    private final EventListenerPort eventListener;

    @KafkaListener(topics = "auth-service.user.created", groupId = "activity-service.user.created.consumers")
    void userCreated(UserCreatedEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "auth-service.login.success", groupId = "activity-service.login.success.consumers")
    void loginSuccess(LoginSuccessEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "auth-service.login.failure", groupId = "activity-service.login.failure.consumers")
    void loginFailure(LoginFailureEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "auth-service.logout.success", groupId = "activity-service.logout.success.consumers")
    void logoutSuccess(LogoutSuccessEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "request-service.request.created", groupId = "activity-service.request.created.consumers")
    void requestCreated(RequestCreatedEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "request-service.request.ready", groupId = "activity-service.request.ready.consumers")
    void requestReady(RequestReadyEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "request-service.request.canceled", groupId = "activity-service.request.canceled.consumers")
    void requestCanceled(RequestCanceledEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "request-service.reservation.created", groupId = "activity-service.reservation.created.consumers")
    void reservationCreated(ReservationCreatedEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "request-service.request.available-to-loan", groupId = "activity-service.request.available-to-loan.consumers")
    void requestAvailableToLoan(RequestAvailableToLoanEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "loan-service.loan.created", groupId = "activity-service.loan.created.consumers")
    void loanCreated(LoanCreatedEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "loan-service.loan.prolonged", groupId = "activity-service.loan.prolonged.consumers")
    void loanProlonged(LoanProlongedEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "loan-service.loan.prolongation.not-allowed", groupId = "activity-service.loan.prolongation.not-allowed.consumers")
    void loanProlongationNotAllowed(LoanProlongationNotAllowed event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "loan-service.book-item.returned", groupId = "activity-service.book-item.returned.consumers")
    void bookItemReturned(BookItemReturnedEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "loan-service.book-item.lost", groupId = "activity-service.book-item.lost.consumers")
    void bookItemLost(BookItemLostEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "fine-service.fine.paid", groupId = "activity-service.fine.paid.consumers")
    void finePaid(FinePaidEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "notification-service.notification.system.sent", groupId = "activity-service.notification.system.sent.consumers")
    void systemNotificationSent(SystemNotificationSentEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "notification-service.notification.sms.sent", groupId = "activity-service.notification.sms.sent.consumers")
    void smsNotificationSent(SmsNotificationSentEvent event) {
        eventListener.handle(event);
    }

    @KafkaListener(topics = "notification-service.notification.email.sent", groupId = "activity-service.notification.email.sent.consumers")
    void emailNotificationSent(EmailNotificationSentEvent event) {
        eventListener.handle(event);
    }
}
