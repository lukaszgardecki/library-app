package com.example.notificationservice.core;

import com.example.notificationservice.domain.i18n.MessageKey;
import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.model.values.NotificationContent;
import com.example.notificationservice.domain.model.values.NotificationSubject;
import com.example.notificationservice.domain.model.values.NotificationType;
import com.example.notificationservice.domain.model.values.UserId;
import com.example.notificationservice.domain.ports.out.MessageProviderPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
class NotificationFactory {
    private final MessageProviderPort msgProvider;

    Notification createUserCreatedNotification(Long userId, String firstname) {
        return createNotification(
                NotificationType.USER_REGISTERED,
                MessageKey.NOTIFICATION_USER_REGISTERED_SUBJECT,
                MessageKey.NOTIFICATION_USER_REGISTERED_CONTENT,
                userId,
                firstname
        );
    }

    Notification createRequestCreatedNotification(Long userId, String bookTitle) {
        return createNotification(
                NotificationType.REQUEST_CREATED,
                MessageKey.NOTIFICATION_REQUEST_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_CREATED_CONTENT,
                userId,
                bookTitle
        );
    }

    Notification createRequestReadyNotification(Long userId, String bookTitle) {
        return createNotification(
                NotificationType.REQUEST_COMPLETED,
                MessageKey.NOTIFICATION_REQUEST_READY_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_READY_CONTENT,
                userId,
                bookTitle
        );
    }

    Notification createRequestCanceledNotification(Long userId, String bookTitle, Long bookItemId) {
        return createNotification(
                NotificationType.REQUEST_CANCELLED,
                MessageKey.NOTIFICATION_REQUEST_CANCELED_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_CANCELED_CONTENT,
                userId,
                bookTitle,
                bookItemId
        );
    }

    Notification createReservationCreatedNotification(Long userId, String bookTitle, int queue, LocalDate loanDueDate) {
        return createNotification(
                NotificationType.BOOK_RESERVED_FIRST_PERSON,
                MessageKey.NOTIFICATION_RESERVATION_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_RESERVATION_CREATED_CONTENT_1,
                userId,
                bookTitle,
                queue,
                loanDueDate
        );
    }

    Notification createRequestAvailableToLoanNotification(Long userId, String bookTitle) {
        return createNotification(
                NotificationType.BOOK_AVAILABLE_TO_BORROW,
                MessageKey.NOTIFICATION_REQUEST_AVAILABLE_T0_LOAN_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_AVAILABLE_TO_LOAN_CONTENT,
                userId,
                bookTitle
        );
    }

    Notification createLoanCreatedNotification(Long userId, String bookTitle) {
        return createNotification(
                NotificationType.BOOK_BORROWED,
                MessageKey.NOTIFICATION_LOAN_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_CREATED_CONTENT,
                userId,
                bookTitle
        );
    }

    Notification createLoanProlongedNotification(Long userId, String bookTitle, LocalDate loanDueDate) {
        return createNotification(
                NotificationType.BOOK_RENEWED,
                MessageKey.NOTIFICATION_LOAN_PROLONGED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_PROLONGED_CONTENT,
                userId,
                bookTitle,
                loanDueDate
        );
    }

    Notification createLoanProlongationNotAllowedNotification(Long userId, String bookTitle) {
        return createNotification(
                NotificationType.RENEWAL_IMPOSSIBLE,
                MessageKey.NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_CONTENT,
                userId,
                bookTitle
        );
    }

    Notification createBookItemReturnedNotification(Long userId, String bookTitle) {
        return createNotification(
                NotificationType.BOOK_RETURNED,
                MessageKey.NOTIFICATION_BOOK_RETURNED_SUBJECT,
                MessageKey.NOTIFICATION_BOOK_RETURNED_CONTENT,
                userId,
                bookTitle
        );
    }

    Notification createBookItemLostNotification(Long userId, String bookTitle, BigDecimal charge) {
        return createNotification(
                NotificationType.BOOK_LOST,
                MessageKey.NOTIFICATION_BOOK_LOST_SUBJECT,
                MessageKey.NOTIFICATION_BOOK_LOST_CONTENT,
                userId,
                bookTitle,
                charge
        );
    }

    private Notification createNotification(
            NotificationType type,
            MessageKey subjectKey,
            MessageKey contentKey,
            Long userId,
            Object ...contentArgs
    ) {
        NotificationSubject subject = new NotificationSubject(msgProvider.getMessage(subjectKey));
        NotificationContent content = new NotificationContent(msgProvider.getMessage(contentKey, contentArgs));
        return new Notification(subject, content, type, new UserId(userId));
    }
}
