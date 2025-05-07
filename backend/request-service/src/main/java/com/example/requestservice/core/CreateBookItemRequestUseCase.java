package com.example.requestservice.core;

import com.example.requestservice.domain.integration.catalog.dto.BookItemDto;
import com.example.requestservice.domain.integration.catalog.BookItemStatus;
import com.example.requestservice.domain.integration.loan.LoanDueDate;
import com.example.requestservice.domain.model.*;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.UserId;
import com.example.requestservice.domain.ports.out.CatalogServicePort;
import com.example.requestservice.domain.ports.out.EventPublisherPort;
import com.example.requestservice.domain.ports.out.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateBookItemRequestUseCase {
    private final UserServicePort userService;
    private final CatalogServicePort catalogService;
    private final BookItemRequestService bookItemRequestService;
    private final EventPublisherPort publisher;

    BookItemRequest execute(BookItemId bookItemId, UserId userId) {
        userService.verifyUserForBookItemRequest(userId);
        bookItemRequestService.verifyIfCurrentRequestExists(bookItemId, userId);
        BookItemDto bookItem = catalogService.verifyAndGetBookItemForRequest(bookItemId);
        BookItemRequest request;
        if (bookItem.getStatus() == BookItemStatus.AVAILABLE) {
            request = bookItemRequestService.saveRequest(bookItemId, userId);
            publisher.publishRequestCreatedEvent(BookItemRequestMapper.toDto(request));
        } else {
            request = bookItemRequestService.saveReservation(bookItemId, userId);
            int queuePosition = bookItemRequestService.getReservationQueuePosition(bookItemId, userId);
            LoanDueDate dueDate = new LoanDueDate(bookItem.getDueDate().atStartOfDay());
            publisher.publishReservationCreatedEvent(bookItemId, userId, queuePosition, dueDate);
        }
        return request;
    }
}
