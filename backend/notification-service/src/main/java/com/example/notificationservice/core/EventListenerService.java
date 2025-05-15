package com.example.notificationservice.core;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.ports.in.EventListenerPort;
import com.example.notificationservice.domain.ports.out.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    public void handleUserCreatedEvent(Long userId, String firstname) {
        Notification notification = notificationFactory.createUserCreatedNotification(userId, firstname);
        saveAndSend(notification);
    }

    @Override
    public void handleRequestCreatedEvent(Long userId, String bookTitle) {
        Notification notification = notificationFactory.createRequestCreatedNotification(userId, bookTitle);
        saveAndSend(notification);
    }

    @Override
    public void handleRequestReadyEvent(Long userId, String bookTitle) {
        Notification notification = notificationFactory.createRequestReadyNotification(userId, bookTitle);
        saveAndSend(notification);
    }

    @Override
    public void handleRequestCanceledEvent(Long userId, String bookTitle, Long bookItemId) {
        Notification notification = notificationFactory.createRequestCanceledNotification(userId, bookTitle, bookItemId);
        saveAndSend(notification);
    }

    @Override
    public void handleReservationCreatedEvent(Long userId, String bookTitle, int queue, LocalDate loanDueDate) {
        Notification notification = notificationFactory.createReservationCreatedNotification(userId, bookTitle, queue, loanDueDate);
        saveAndSend(notification);
    }

    @Override
    public void handleRequestAvailableToLoanEvent(Long userId, String bookTitle) {
        Notification notification = notificationFactory.createRequestAvailableToLoanNotification(userId, bookTitle);
        saveAndSend(notification);
    }

    @Override
    public void handleLoanCreatedEvent(Long userId, String bookTitle) {
        Notification notification = notificationFactory.createLoanCreatedNotification(userId, bookTitle);
        saveAndSend(notification);
    }

    @Override
    public void handleLoanProlongedEvent(Long userId, String bookTitle, LocalDate loanDueDate) {
        Notification notification = notificationFactory.createLoanProlongedNotification(userId, bookTitle, loanDueDate);
        saveAndSend(notification);
    }

    @Override
    public void handleLoanProlongationNotAllowedEvent(Long userId, String bookTitle) {
        Notification notification = notificationFactory.createLoanProlongationNotAllowedNotification(userId, bookTitle);
        saveAndSend(notification);
    }

    @Override
    public void handleBookItemReturnedEvent(Long userId, String bookTitle) {
        Notification notification = notificationFactory.createBookItemReturnedNotification(userId, bookTitle);
        saveAndSend(notification);
    }

    @Override
    public void handleBookItemLostEvent(Long userId, String bookTitle, BigDecimal charge) {
        Notification notification = notificationFactory.createBookItemLostNotification(userId, bookTitle, charge);
        saveAndSend(notification);
    }
}