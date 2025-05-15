package com.example.notificationservice.domain.ports.in;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EventListenerPort {

    void handleUserCreatedEvent(Long userId, String firstname);

    void handleRequestCreatedEvent(Long userId, String bookTitle);

    void handleRequestReadyEvent(Long userId, String bookTitle);

    void handleRequestCanceledEvent(Long userId, String bookTitle, Long bookItemId);

    void handleReservationCreatedEvent(Long userId, String bookTitle, int queue, LocalDate loanDueDate);

    void handleRequestAvailableToLoanEvent(Long userId, String bookTitle);

    void handleLoanCreatedEvent(Long userId, String bookTitle);

    void handleLoanProlongedEvent(Long userId, String bookTitle, LocalDate loanDueDate);

    void handleLoanProlongationNotAllowedEvent(Long userId, String bookTitle);

    void handleBookItemReturnedEvent(Long userId, String bookTitle);

    void handleBookItemLostEvent(Long userId, String bookTitle, BigDecimal charge);
}
