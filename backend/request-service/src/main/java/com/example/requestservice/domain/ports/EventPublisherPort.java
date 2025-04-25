package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.dto.BookItemRequestDto;
import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.LoanDueDate;
import com.example.requestservice.domain.model.UserId;

public interface EventPublisherPort {

    void publishBookItemRequestReadyEvent(BookItemId bookItemId, UserId userId);

    void publishBookItemRequestCanceledEvent(BookItemId bookItemId, UserId userId);

    void publishBookItemRequestedEvent(BookItemRequestDto dto);

    void publishBookItemReservedEvent(BookItemId bookItemId, UserId userId, int queuePosition, LoanDueDate dueDate);

    void publishBookItemAvailableToLoan(BookItemId bookItemId, UserId userId);
}
