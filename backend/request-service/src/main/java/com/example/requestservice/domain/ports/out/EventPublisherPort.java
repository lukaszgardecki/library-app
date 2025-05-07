package com.example.requestservice.domain.ports.out;

import com.example.requestservice.domain.dto.BookItemRequestDto;
import com.example.requestservice.domain.integration.catalog.BookId;
import com.example.requestservice.domain.integration.loan.LoanDueDate;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.UserId;

public interface EventPublisherPort {

    void publishRequestReadyEvent(BookItemId bookItemId, UserId userId);

    void publishRequestCanceledEvent(BookItemId bookItemId, UserId userId, BookId bookId);

    void publishRequestCreatedEvent(BookItemRequestDto dto);

    void publishReservationCreatedEvent(BookItemId bookItemId, UserId userId, int queuePosition, LoanDueDate dueDate);

    void publishRequestAvailableToLoanEvent(BookItemId bookItemId, UserId userId);
}
