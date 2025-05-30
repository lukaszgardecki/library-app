package com.example.util;

import com.example.activityservice.infrastructure.kafka.event.incoming.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class EventTestFactory {

    public static LoginFailureEvent createLoginFailureEvent() {
        return new LoginFailureEvent(
                1L, "testFirstname", "testLastname"
        );
    }

    public static LoginSuccessEvent createLoginSuccessEvent() {
        return new LoginSuccessEvent(
                1L, "testFirstname", "testLastname"
        );
    }

    public static LogoutSuccessEvent createLogoutSuccessEvent() {
        return new LogoutSuccessEvent(
                1L, "testFirstname", "testLastname"
        );
    }

    public static UserCreatedEvent createUserCreatedEvent() {
        return new UserCreatedEvent(
                1L, "testFirstname", "testLastname",
                LocalDate.of(1990, 1, 1),
                "testCity"
        );
    }

    public static BookItemLostEvent createBookItemLostEvent() {
        return new BookItemLostEvent(
                1L,
                LocalDate.of(1990, 1, 2),
                LocalDate.of(1990, 1, 1),
                2L, 3L, 4L, new BigDecimal("3"), "testTitle"
        );
    }

    public static BookItemReturnedEvent createBookItemReturnedEvent() {
        return new BookItemReturnedEvent(
                1L,
                LocalDate.of(1990, 1, 2),
                LocalDate.of(1990, 1, 1),
                2L, 3L, "testTitle"
        );
    }

    public static LoanCreatedEvent createLoanCreatedEvent(boolean isReferenceOnly) {
        return new LoanCreatedEvent(
                1L,
                LocalDateTime.of(1990, 1, 2, 8, 0),
                LocalDate.of(1990, 1, 1),
                LocalDateTime.of(1990, 1, 2, 8, 0),
                2L, 3L, 4L, isReferenceOnly, "testTitle", "testSubject"
        );
    }

    public static LoanProlongedEvent createLoanProlongedEvent() {
        return new LoanProlongedEvent(
                1L,
                LocalDate.of(1990, 1, 1),
                LocalDateTime.of(1990, 1, 2, 8, 0),
                2L, 3L, "testTitle"
        );
    }

    public static RequestCanceledEvent createRequestCanceledEvent() {
        return new RequestCanceledEvent(
                1L, 2L, 3L, "testTitle"
        );
    }

    public static RequestCreatedEvent createRequestCreatedEvent() {
        return new RequestCreatedEvent(
                1L,
                LocalDateTime.of(1990, 1, 2, 8, 0),
                "testSTATUS",
                2L, 3L, "testTitle"
        );
    }

    public static RequestReadyEvent createRequestReadyEvent() {
        return new RequestReadyEvent(
                1L, 2L, 3L, "testTitle"
        );
    }

    public static ReservationCreatedEvent createReservationCreatedEvent(int queuePosition) {
        return new ReservationCreatedEvent(
                1L, 2L, queuePosition, LocalDate.of(1990, 1, 1), "testTitle"
        );
    }

    public static EmailNotificationSentEvent createEmailNotificationSentEvent() {
        return new EmailNotificationSentEvent(1L, "testSubject");
    }

    public static SmsNotificationSentEvent createSmsNotificationSentEvent() {
        return new SmsNotificationSentEvent(1L, "testSubject");
    }

    public static SystemNotificationSentEvent createSystemNotificationSentEvent() {
        return new SystemNotificationSentEvent(1L, "testSubject");
    }
}
