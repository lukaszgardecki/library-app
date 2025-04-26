package com.example.notificationservice.core;

import com.example.notificationservice.domain.MessageKey;
import com.example.notificationservice.domain.dto.BookDto;
import com.example.notificationservice.domain.event.incoming.*;
import com.example.notificationservice.domain.model.*;
import com.example.notificationservice.domain.ports.CatalogServicePort;
import com.example.notificationservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationFactory {
    private final MessageProviderPort msgProvider;
    private final CatalogServicePort catalogService;

    Notification createUserCreatedNotification(UserCreatedEvent event) {
        return createUserRelatedNotification(
                event.getUserId(),
                NotificationType.USER_REGISTERED,
                MessageKey.NOTIFICATION_USER_REGISTERED_SUBJECT,
                MessageKey.NOTIFICATION_USER_REGISTERED_CONTENT,
                event.getFirstName().value()
        );
    }

    Notification createRequestCreatedNotification(RequestCreatedEvent event) {
        return createBookRelatedNotification(
                NotificationType.REQUEST_CREATED,
                MessageKey.NOTIFICATION_REQUEST_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_CREATED_CONTENT,
                event.getUserId(),
                event.getBookItemId()
        );
    }

    Notification createRequestReadyNotification(RequestReadyEvent event) {
        return createBookRelatedNotification(
                NotificationType.REQUEST_COMPLETED,
                MessageKey.NOTIFICATION_REQUEST_READY_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_READY_CONTENT,
                event.getUserId(),
                event.getBookItemId()
        );
    }

    Notification createRequestCanceledNotification(RequestCanceledEvent event) {
        return createBookRelatedNotification(
                NotificationType.REQUEST_CANCELLED,
                MessageKey.NOTIFICATION_REQUEST_CANCELED_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_CANCELED_CONTENT,
                event.getUserId(),
                event.getBookItemId()
        );
    }

    Notification createReservationCreatedNotification(ReservationCreatedEvent event) {
        return createBookRelatedNotification(
                NotificationType.BOOK_RESERVED_FIRST_PERSON,
                MessageKey.NOTIFICATION_RESERVATION_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_RESERVATION_CREATED_CONTENT_1,
                event.getUserId(),
                event.getBookItemId(),
                event.getQueue(),
                event.getLoanDueDate()
        );
    }

    Notification createRequestAvailableToLoanNotification(RequestAvailableToLoanEvent event) {
        return createBookRelatedNotification(
                NotificationType.BOOK_AVAILABLE_TO_BORROW,
                MessageKey.NOTIFICATION_REQUEST_AVAILABLE_T0_LOAN_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_AVAILABLE_TO_LOAN_CONTENT,
                event.getUserId(),
                event.getBookItemId()
        );
    }

    Notification createLoanCreatedNotification(LoanCreatedEvent event) {
        return createBookRelatedNotification(
                NotificationType.BOOK_BORROWED,
                MessageKey.NOTIFICATION_LOAN_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_CREATED_CONTENT,
                event.getUserId(),
                event.getBookItemId()
        );
    }

    Notification createLoanProlongedNotification(LoanProlongedEvent event) {
        return createBookRelatedNotification(
                NotificationType.BOOK_RENEWED,
                MessageKey.NOTIFICATION_LOAN_PROLONGED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_PROLONGED_CONTENT,
                event.getUserId(),
                event.getBookItemId(),
                event.getLoanDueDate()
        );
    }

    Notification createLoanProlongationNotAllowedNotification(LoanProlongationNotAllowed event) {
        return createBookRelatedNotification(
                NotificationType.RENEWAL_IMPOSSIBLE,
                MessageKey.NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_CONTENT,
                event.getUserId(),
                event.getBookItemId()
        );
    }

    Notification createBookItemReturnedNotification(BookItemReturnedEvent event) {
        return createBookRelatedNotification(
                NotificationType.BOOK_RETURNED,
                MessageKey.NOTIFICATION_BOOK_RETURNED_SUBJECT,
                MessageKey.NOTIFICATION_BOOK_RETURNED_CONTENT,
                event.getUserId(),
                event.getBookItemId()
        );
    }

    Notification createBookItemLostNotification(BookItemLostEvent event) {
        return createBookRelatedNotification(
                NotificationType.BOOK_LOST,
                MessageKey.NOTIFICATION_BOOK_LOST_SUBJECT,
                MessageKey.NOTIFICATION_BOOK_LOST_CONTENT,
                event.getUserId(),
                event.getBookItemId(),
                event.getCharge()
        );
    }

    private Notification createBookRelatedNotification(
            NotificationType type,
            MessageKey subjectKey,
            MessageKey contentKey,
            UserId userId,
            BookItemId bookItemId,
            Object ...contentArgs
    ) {
        BookDto book = catalogService.getBookByBookItemId(bookItemId);
        NotificationSubject subject = new NotificationSubject(msgProvider.getMessage(subjectKey));
        NotificationContent content = new NotificationContent(msgProvider.getMessage(contentKey, book.getTitle(), contentArgs));
        return new Notification(subject, content, type, userId);
    }

    private Notification createUserRelatedNotification(
            UserId userId,
            NotificationType type,
            MessageKey subjectKey,
            MessageKey contentKey,
            Object ...contentArgs
    ) {
        NotificationSubject subject = new NotificationSubject(msgProvider.getMessage(subjectKey));
        NotificationContent content = new NotificationContent(msgProvider.getMessage(contentKey, contentArgs));
        return new Notification(subject, content, type, userId);
    }
}
