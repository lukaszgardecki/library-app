package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.dto.BookItemRequestDto;
import com.example.requestservice.domain.model.*;

public interface EventPublisherPort {

    void publishRequestReadyEvent(BookItemId bookItemId, UserId userId);

    void publishRequestCanceledEvent(BookItemId bookItemId, UserId userId, BookId bookId);

    void publishRequestCreatedEvent(BookItemRequestDto dto);

    void publishReservationCreatedEvent(BookItemId bookItemId, UserId userId, int queuePosition, LoanDueDate dueDate);

    void publishRequestAvailableToLoanEvent(BookItemId bookItemId, UserId userId);
}
