package com.example.notificationservice.core;

import com.example.notificationservice.domain.event.incoming.*;
import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.ports.in.EventListenerPort;
import com.example.notificationservice.domain.ports.out.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EventListenerService implements EventListenerPort {
    private final NotificationRepositoryPort notificationRepository;
    private final NotificationFactory notificationFactory;
    private final NotificationSender sender;

    private void saveAndSend(Notification notification) {
        System.out.printf("Wysyłam powiadomienie (%s)%n", notification.getClass().getSimpleName());
        notificationRepository.save(notification);
        sender.send(notification);
    }
    @Override
    public void handleUserCreatedEvent(UserCreatedEvent event) {
        Notification notification = notificationFactory.createUserCreatedNotification(event);
        saveAndSend(notification);
    }

    @Override
    public void handleRequestCreatedEvent(RequestCreatedEvent event) {
        Notification notification = notificationFactory.createRequestCreatedNotification(event);
        saveAndSend(notification);
    }

    @Override
    public void handleRequestReadyEvent(RequestReadyEvent event) {
        Notification notification = notificationFactory.createRequestReadyNotification(event);
        saveAndSend(notification);
    }

    @Override
    public void handleRequestCanceledEvent(RequestCanceledEvent event) {
        Notification notification = notificationFactory.createRequestCanceledNotification(event);
        saveAndSend(notification);
    }

    @Override
    public void handleReservationCreatedEvent(ReservationCreatedEvent event) {
        Notification notification = notificationFactory.createReservationCreatedNotification(event);
        saveAndSend(notification);
    }

    @Override
    public void handleRequestAvailableToLoanEvent(RequestAvailableToLoanEvent event) {
        Notification notification = notificationFactory.createRequestAvailableToLoanNotification(event);
        saveAndSend(notification);
    }

    @Override
    public void handleLoanCreatedEvent(LoanCreatedEvent event) {
        Notification notification = notificationFactory.createLoanCreatedNotification(event);
        saveAndSend(notification);
    }

    @Override
    public void handleLoanProlongedEvent(LoanProlongedEvent event) {
        Notification notification = notificationFactory.createLoanProlongedNotification(event);
        saveAndSend(notification);
    }

    @Override
    public void handleLoanProlongationNotAllowedEvent(LoanProlongationNotAllowed event) {
        Notification notification = notificationFactory.createLoanProlongationNotAllowedNotification(event);
        saveAndSend(notification);
    }

    @Override
    public void handleBookItemReturnedEvent(BookItemReturnedEvent event) {
        Notification notification = notificationFactory.createBookItemReturnedNotification(event);
        saveAndSend(notification);
    }

    @Override
    public void handleBookItemLostEvent(BookItemLostEvent event) {
        Notification notification = notificationFactory.createBookItemLostNotification(event);
        saveAndSend(notification);
    }
}