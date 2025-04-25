package com.example.requestservice.infrastructure.events;

import com.example.requestservice.domain.dto.BookItemRequestDto;
import com.example.requestservice.domain.event.outgoing.*;
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

    private static final String BOOK_ITEM_REQUEST_READY_TOPIC = "book-item-request.ready";
    private static final String BOOK_ITEM_REQUEST_CANCELED_TOPIC = "book-item-request.canceled";
    private static final String BOOK_ITEM_REQUESTED_TOPIC = "book-item.requested";
    private static final String BOOK_ITEM_RESERVED_TOPIC = "book-item.reserved";
    private static final String BOOK_ITEM_AVAILABLE_TO_LOAN_TOPIC = "book-item.available-to-loan";

    @Override
    public void publishBookItemRequestReadyEvent(BookItemId bookItemId, UserId userId) {
        template.send(BOOK_ITEM_REQUEST_READY_TOPIC, new BookItemRequestReadyEvent(bookItemId, userId));
    }

    @Override
    public void publishBookItemRequestCanceledEvent(BookItemId bookItemId, UserId userId) {
        template.send(BOOK_ITEM_REQUEST_CANCELED_TOPIC, new BookItemRequestCanceledEvent(bookItemId, userId));
    }

    @Override
    public void publishBookItemRequestedEvent(BookItemRequestDto dto) {
        template.send(BOOK_ITEM_REQUESTED_TOPIC, new BookItemRequestedEvent(dto));
    }

    @Override
    public void publishBookItemReservedEvent(BookItemId bookItemId, UserId userId, int queuePosition, LoanDueDate dueDate) {
        template.send(BOOK_ITEM_RESERVED_TOPIC, new BookItemReservedEvent(bookItemId, userId, queuePosition, dueDate));
    }

    @Override
    public void publishBookItemAvailableToLoan(BookItemId bookItemId, UserId userId) {
        template.send(BOOK_ITEM_AVAILABLE_TO_LOAN_TOPIC, new BookItemAvailableToLoanEvent(bookItemId, userId));
    }
}
