package com.example.requestservice.infrastructure.kafka;

import com.example.requestservice.domain.integration.catalog.BookId;
import com.example.requestservice.domain.integration.catalog.dto.BookDto;
import com.example.requestservice.domain.integration.loan.LoanDueDate;
import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.UserId;
import com.example.requestservice.domain.ports.out.CatalogServicePort;
import com.example.requestservice.domain.ports.out.EventPublisherPort;
import com.example.requestservice.infrastructure.http.BookItemRequestMapper;
import com.example.requestservice.infrastructure.kafka.event.outgoing.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaPublisherAdapter implements EventPublisherPort {
    private final CatalogServicePort catalogService;
    private final KafkaTemplate<String, Object> template;
    private final BookItemRequestMapper mapper;

    private static final String REQUEST_READY_TOPIC = "request-service.request.ready";
    private static final String REQUEST_CANCELED_TOPIC = "request-service.request.canceled";
    private static final String REQUEST_CREATED_TOPIC = "request-service.request.created";
    private static final String RESERVATION_CREATED_TOPIC = "request-service.reservation.created";
    private static final String REQUEST_AVAILABLE_TO_LOAN_TOPIC = "request-service.request.available-to-loan";

    @Override
    public void publishRequestReadyEvent(BookItemId bookItemId, UserId userId) {
        BookDto book = catalogService.getBookByBookItemId(bookItemId);
        template.send(REQUEST_READY_TOPIC, new RequestReadyEvent(
                bookItemId.value(), userId.value(), book.getTitle()
        ));
    }

    @Override
    public void publishRequestCanceledEvent(BookItemId bookItemId, UserId userId, BookId bookId) {
        BookDto book = catalogService.getBookByBookItemId(bookItemId);
        template.send(REQUEST_CANCELED_TOPIC, new RequestCanceledEvent(
                bookItemId.value(), userId.value(), bookId.value(), book.getTitle()
        ));
    }

    @Override
    public void publishRequestCreatedEvent(BookItemRequest request) {
        BookDto book = catalogService.getBookByBookItemId(new BookItemId(request.getBookItemId().value()));
        template.send(REQUEST_CREATED_TOPIC, new RequestCreatedEvent(
                mapper.toDto(request), book.getTitle()
        ));
    }

    @Override
    public void publishReservationCreatedEvent(BookItemId bookItemId, UserId userId, int queuePosition, LoanDueDate dueDate) {
        BookDto book = catalogService.getBookByBookItemId(bookItemId);
        template.send(RESERVATION_CREATED_TOPIC, new ReservationCreatedEvent(
                bookItemId.value(), userId.value(), queuePosition, dueDate.value(), book.getTitle()
        ));
    }

    @Override
    public void publishRequestAvailableToLoanEvent(BookItemId bookItemId, UserId userId) {
        BookDto book = catalogService.getBookByBookItemId(bookItemId);
        template.send(REQUEST_AVAILABLE_TO_LOAN_TOPIC, new RequestAvailableToLoanEvent(
                bookItemId.value(), userId.value(), book.getTitle()
        ));
    }
}
