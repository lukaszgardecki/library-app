package com.example.notificationservice.core;

import com.example.notificationservice.domain.MessageKey;
import com.example.notificationservice.domain.dto.BookDto;
import com.example.notificationservice.domain.event.incoming.*;
import com.example.notificationservice.domain.model.*;
import com.example.notificationservice.domain.ports.CatalogServicePort;
import com.example.notificationservice.domain.ports.MessageProviderPort;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.stream.Stream;

@RequiredArgsConstructor
class NotificationFactory {
    private final MessageProviderPort msgProvider;
    private final CatalogServicePort catalogService;

    Notification createUserCreatedNotification(UserCreatedEvent event) {
        return createUserRelatedNotification(
                NotificationType.USER_REGISTERED,
                MessageKey.NOTIFICATION_USER_REGISTERED_SUBJECT,
                MessageKey.NOTIFICATION_USER_REGISTERED_CONTENT,
                event.getUserId(),
                event.getFirstName().value()
        );
    }

    Notification createRequestCreatedNotification(RequestCreatedEvent event) {
        BookDto book = catalogService.getBookByBookItemId(event.getBookItemId());
        return createBookRelatedNotification(
                NotificationType.REQUEST_CREATED,
                MessageKey.NOTIFICATION_REQUEST_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_CREATED_CONTENT,
                event.getUserId(),
                book
        );
    }

    Notification createRequestReadyNotification(RequestReadyEvent event) {
        BookDto book = catalogService.getBookByBookItemId(event.getBookItemId());
        return createBookRelatedNotification(
                NotificationType.REQUEST_COMPLETED,
                MessageKey.NOTIFICATION_REQUEST_READY_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_READY_CONTENT,
                event.getUserId(),
                book
        );
    }

    Notification createRequestCanceledNotification(RequestCanceledEvent event) {
        BookDto book = catalogService.getBookById(event.getBookId());
        return createBookRelatedNotification(
                NotificationType.REQUEST_CANCELLED,
                MessageKey.NOTIFICATION_REQUEST_CANCELED_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_CANCELED_CONTENT,
                event.getUserId(),
                book,
                event.getBookItemId().value()
        );
    }

    Notification createReservationCreatedNotification(ReservationCreatedEvent event) {
        BookDto book = catalogService.getBookByBookItemId(event.getBookItemId());
        return createBookRelatedNotification(
                NotificationType.BOOK_RESERVED_FIRST_PERSON,
                MessageKey.NOTIFICATION_RESERVATION_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_RESERVATION_CREATED_CONTENT_1,
                event.getUserId(),
                book,
                event.getQueue(),
                event.getLoanDueDate().value()
        );
    }

    Notification createRequestAvailableToLoanNotification(RequestAvailableToLoanEvent event) {
        BookDto book = catalogService.getBookByBookItemId(event.getBookItemId());
        return createBookRelatedNotification(
                NotificationType.BOOK_AVAILABLE_TO_BORROW,
                MessageKey.NOTIFICATION_REQUEST_AVAILABLE_T0_LOAN_SUBJECT,
                MessageKey.NOTIFICATION_REQUEST_AVAILABLE_TO_LOAN_CONTENT,
                event.getUserId(),
                book
        );
    }

    Notification createLoanCreatedNotification(LoanCreatedEvent event) {
        BookDto book = catalogService.getBookByBookItemId(event.getBookItemId());
        return createBookRelatedNotification(
                NotificationType.BOOK_BORROWED,
                MessageKey.NOTIFICATION_LOAN_CREATED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_CREATED_CONTENT,
                event.getUserId(),
                book
        );
    }

    Notification createLoanProlongedNotification(LoanProlongedEvent event) {
        BookDto book = catalogService.getBookByBookItemId(event.getBookItemId());
        return createBookRelatedNotification(
                NotificationType.BOOK_RENEWED,
                MessageKey.NOTIFICATION_LOAN_PROLONGED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_PROLONGED_CONTENT,
                event.getUserId(),
                book,
                event.getLoanDueDate().value()
        );
    }

    Notification createLoanProlongationNotAllowedNotification(LoanProlongationNotAllowed event) {
        BookDto book = catalogService.getBookByBookItemId(event.getBookItemId());
        return createBookRelatedNotification(
                NotificationType.RENEWAL_IMPOSSIBLE,
                MessageKey.NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_SUBJECT,
                MessageKey.NOTIFICATION_LOAN_PROLONGATION_NOT_ALLOWED_CONTENT,
                event.getUserId(),
                book
        );
    }

    Notification createBookItemReturnedNotification(BookItemReturnedEvent event) {
        BookDto book = catalogService.getBookByBookItemId(event.getBookItemId());
        return createBookRelatedNotification(
                NotificationType.BOOK_RETURNED,
                MessageKey.NOTIFICATION_BOOK_RETURNED_SUBJECT,
                MessageKey.NOTIFICATION_BOOK_RETURNED_CONTENT,
                event.getUserId(),
                book
        );
    }

    Notification createBookItemLostNotification(BookItemLostEvent event) {
        BookDto book = catalogService.getBookById(event.getBookId());
        return createBookRelatedNotification(
                NotificationType.BOOK_LOST,
                MessageKey.NOTIFICATION_BOOK_LOST_SUBJECT,
                MessageKey.NOTIFICATION_BOOK_LOST_CONTENT,
                event.getUserId(),
                book,
                event.getCharge().value()
        );
    }

    private Notification createBookRelatedNotification(
            NotificationType type,
            MessageKey subjectKey,
            MessageKey contentKey,
            UserId userId,
            BookDto book,
            Object ...contentArgs
    ) {
        Object[] allArgs = Stream.concat(Stream.of(book.getTitle()), Arrays.stream(contentArgs)).toArray();
        NotificationSubject subject = new NotificationSubject(msgProvider.getMessage(subjectKey));
        NotificationContent content = new NotificationContent(msgProvider.getMessage(contentKey, allArgs));
        return new Notification(subject, content, type, userId);
    }

    private Notification createUserRelatedNotification(
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
