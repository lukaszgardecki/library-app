package com.example.requestservice.infrastructure.events;

import com.example.requestservice.domain.dto.BookDto;
import com.example.requestservice.domain.dto.BookItemRequestDto;
import com.example.requestservice.domain.event.outgoing.*;
import com.example.requestservice.domain.model.*;
import com.example.requestservice.domain.ports.CatalogServicePort;
import com.example.requestservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final CatalogServicePort catalogService;
    private final KafkaTemplate<String, Object> template;

    private static final String REQUEST_READY_TOPIC = "request-service.request.ready";
    private static final String REQUEST_CANCELED_TOPIC = "request-service.request.canceled";
    private static final String REQUEST_CREATED_TOPIC = "request-service.request.created";
    private static final String RESERVATION_CREATED_TOPIC = "request-service.reservation.created";
    private static final String REQUEST_AVAILABLE_TO_LOAN_TOPIC = "request-service.request.available-to-loan";

    @Override
    public void publishRequestReadyEvent(BookItemId bookItemId, UserId userId) {
        BookDto book = catalogService.getBookByBookItemId(bookItemId);
        template.send(REQUEST_READY_TOPIC, new RequestReadyEvent(
                bookItemId, userId, new Title(book.getTitle())
        ));
    }

    @Override
    public void publishRequestCanceledEvent(BookItemId bookItemId, UserId userId, BookId bookId) {
        BookDto book = catalogService.getBookByBookItemId(bookItemId);
        template.send(REQUEST_CANCELED_TOPIC, new RequestCanceledEvent(
                bookItemId, userId, bookId, new Title(book.getTitle())
        ));
    }

    @Override
    public void publishRequestCreatedEvent(BookItemRequestDto dto) {
        BookDto book = catalogService.getBookByBookItemId(new BookItemId(dto.getBookItemId()));
        template.send(REQUEST_CREATED_TOPIC, new RequestCreatedEvent(
                dto, new Title(book.getTitle())
        ));
    }

    @Override
    public void publishReservationCreatedEvent(BookItemId bookItemId, UserId userId, int queuePosition, LoanDueDate dueDate) {
        BookDto book = catalogService.getBookByBookItemId(bookItemId);
        template.send(RESERVATION_CREATED_TOPIC, new ReservationCreatedEvent(
                bookItemId, userId, queuePosition, dueDate, new Title(book.getTitle())
        ));
    }

    @Override
    public void publishRequestAvailableToLoanEvent(BookItemId bookItemId, UserId userId) {
        BookDto book = catalogService.getBookByBookItemId(bookItemId);
        template.send(REQUEST_AVAILABLE_TO_LOAN_TOPIC, new RequestAvailableToLoanEvent(
                bookItemId, userId, new Title(book.getTitle())
        ));
    }
}
