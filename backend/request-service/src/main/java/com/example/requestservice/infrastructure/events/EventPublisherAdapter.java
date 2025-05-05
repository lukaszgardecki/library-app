package com.example.requestservice.infrastructure.events;

import com.example.requestservice.domain.dto.BookItemRequestDto;
import com.example.requestservice.domain.event.outgoing.*;
import com.example.requestservice.domain.model.BookId;
import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.LoanDueDate;
import com.example.requestservice.domain.model.UserId;
import com.example.requestservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String REQUEST_READY_TOPIC = "request-service.request.ready";
    private static final String REQUEST_CANCELED_TOPIC = "request-service.request.canceled";
    private static final String REQUEST_CREATED_TOPIC = "request-service.request.created";
    private static final String RESERVATION_CREATED_TOPIC = "request-service.reservation.created";
    private static final String REQUEST_AVAILABLE_TO_LOAN_TOPIC = "request-service.request.available-to-loan";

    @Override
    public void publishRequestReadyEvent(BookItemId bookItemId, UserId userId) {
        template.send(REQUEST_READY_TOPIC, new RequestReadyEvent(bookItemId, userId));
    }

    @Override
    public void publishRequestCanceledEvent(BookItemId bookItemId, UserId userId, BookId bookId) {
        template.send(REQUEST_CANCELED_TOPIC, new RequestCanceledEvent(bookItemId, userId, bookId));
    }

    @Override
    public void publishRequestCreatedEvent(BookItemRequestDto dto) {
        template.send(REQUEST_CREATED_TOPIC, new RequestCreatedEvent(dto));
    }

    @Override
    public void publishReservationCreatedEvent(BookItemId bookItemId, UserId userId, int queuePosition, LoanDueDate dueDate) {
        template.send(RESERVATION_CREATED_TOPIC, new ReservationCreatedEvent(bookItemId, userId, queuePosition, dueDate));
    }

    @Override
    public void publishRequestAvailableToLoanEvent(BookItemId bookItemId, UserId userId) {
        template.send(REQUEST_AVAILABLE_TO_LOAN_TOPIC, new RequestAvailableToLoanEvent(bookItemId, userId));
    }
}
