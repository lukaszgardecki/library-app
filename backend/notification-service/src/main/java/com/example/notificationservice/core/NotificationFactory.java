package com.example.notificationservice.core;

import com.example.notificationservice.domain.i18n.MessageKey;
import com.example.notificationservice.domain.event.incoming.*;
import com.example.notificationservice.domain.model.*;
import com.example.notificationservice.domain.model.values.NotificationContent;
import com.example.notificationservice.domain.model.values.NotificationSubject;
import com.example.notificationservice.domain.model.values.NotificationType;
import com.example.notificationservice.domain.model.values.UserId;
import com.example.notificationservice.domain.ports.out.MessageProviderPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationFactory {
    private final MessageProviderPort msgProvider;

    Notification createUserCreatedNotification(UserCreatedEvent event) {
        return createNotification(
                NotificationType.USER_REGISTERED,
                MessageKey.NOTIFICATION_USER_REGISTERED_SUBJECT,
                MessageKey.NOTIFICATION_USER_REGISTERED_CONTENT,
                event.getUserId(),
                event.getFirstName().value()
        );
    }

    Notification createRequestCreatedNotification(RequestCreatedEvent event) {
        return createNotification(
                NotificationType.REQUEST_CREATED,
                MessageKey.NOTIFICATION_REQUEST_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_CREATED_CONTENT,
                event.getUserId(),
                event.getBookTitle()
        );
    }

    Notification createRequestReadyNotification(RequestReadyEvent event) {
        return createNotification(
                NotificationType.REQUEST_COMPLETED,
                MessageKey.NOTIFICATION_REQUEST_READY_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_READY_CONTENT,
                event.getUserId(),
                event.getBookTitle()
        );
    }

    Notification createRequestCanceledNotification(RequestCanceledEvent event) {
        return createNotification(
                NotificationType.REQUEST_CANCELLED,
                MessageKey.NOTIFICATION_REQUEST_CANCELED_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_CANCELED_CONTENT,
                event.getUserId(),
                event.getBookTitle(),
                event.getBookItemId().value()
        );
    }

    Notification createReservationCreatedNotification(ReservationCreatedEvent event) {
        return createNotification(
                NotificationType.BOOK_RESERVED_FIRST_PERSON,
                MessageKey.NOTIFICATION_RESERVATION_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_RESERVATION_CREATED_CONTENT_1,
                event.getUserId(),
                event.getBookTitle(),
                event.getQueue(),
                event.getLoanDueDate().value()
        );
    }

    Notification createRequestAvailableToLoanNotification(RequestAvailableToLoanEvent event) {
        return createNotification(
                NotificationType.BOOK_AVAILABLE_TO_BORROW,
                MessageKey.NOTIFICATION_REQUEST_AVAILABLE_T0_LOAN_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_AVAILABLE_TO_LOAN_CONTENT,
                event.getUserId(),
                event.getBookTitle()
        );
    }

    Notification createLoanCreatedNotification(LoanCreatedEvent event) {
        return createNotification(
                NotificationType.BOOK_BORROWED,
                MessageKey.NOTIFICATION_LOAN_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_CREATED_CONTENT,
                event.getUserId(),
                event.getBookTitle()
        );
    }

    Notification createLoanProlongedNotification(LoanProlongedEvent event) {
        return createNotification(
                NotificationType.BOOK_RENEWED,
                MessageKey.NOTIFICATION_LOAN_PROLONGED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_PROLONGED_CONTENT,
                event.getUserId(),
                event.getBookTitle(),
                event.getLoanDueDate().value()
        );
    }

    Notification createLoanProlongationNotAllowedNotification(LoanProlongationNotAllowed event) {
        return createNotification(
                NotificationType.RENEWAL_IMPOSSIBLE,
                MessageKey.NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_CONTENT,
                event.getUserId(),
                event.getBookTitle()
        );
    }

    Notification createBookItemReturnedNotification(BookItemReturnedEvent event) {
        return createNotification(
                NotificationType.BOOK_RETURNED,
                MessageKey.NOTIFICATION_BOOK_RETURNED_SUBJECT,
                MessageKey.NOTIFICATION_BOOK_RETURNED_CONTENT,
                event.getUserId(),
                event.getBookTitle()
        );
    }

    Notification createBookItemLostNotification(BookItemLostEvent event) {
        return createNotification(
                NotificationType.BOOK_LOST,
                MessageKey.NOTIFICATION_BOOK_LOST_SUBJECT,
                MessageKey.NOTIFICATION_BOOK_LOST_CONTENT,
                event.getUserId(),
                event.getBookTitle(),
                event.getCharge().value()
        );
    }

    private Notification createNotification(
            NotificationType type,
            MessageKey subjectKey,
            MessageKey contentKey,
            UserId userId,
            Object ...contentArgs
    ) {
        NotificationSubject subject = new NotificationSubject(msgProvider.getMessage(subjectKey));
        NotificationContent content = new NotificationContent(msgProvider.getMessage(contentKey, contentArgs));
        return new Notification(subject, content, type, userId);
    }
}
