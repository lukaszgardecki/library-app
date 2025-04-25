package com.example.requestservice.core;

import com.example.requestservice.domain.dto.BookItemDto;
import com.example.requestservice.domain.model.*;
import com.example.requestservice.domain.ports.CatalogServicePort;
import com.example.requestservice.domain.ports.EventPublisherPort;
import com.example.requestservice.domain.ports.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateBookItemRequestUseCase {
    private final UserServicePort userService;
//    private final AuthenticationFacade authFacade;
    private final CatalogServicePort catalogService;
    private final BookItemRequestService bookItemRequestService;
    private final EventPublisherPort publisher;

    BookItemRequest execute(BookItemId bookItemId, UserId userId) {
//        authFacade.validateOwnerOrAdminAccess(userId);
        userService.verifyUserForBookItemRequest(userId);
        bookItemRequestService.verifyIfCurrentRequestExists(bookItemId, userId);
        BookItemDto bookItem = catalogService.verifyAndGetBookItemForRequest(bookItemId);
        BookItemRequest request;
        if (bookItem.getStatus() == BookItemStatus.AVAILABLE) {
            request = bookItemRequestService.saveRequest(bookItemId, userId);
            publisher.publishBookItemRequestedEvent(BookItemRequestMapper.toDto(request));
        } else {
            request = bookItemRequestService.saveReservation(bookItemId, userId);
            int queuePosition = bookItemRequestService.getReservationQueuePosition(bookItemId, userId);
            LoanDueDate dueDate = new LoanDueDate(bookItem.getDueDate().atStartOfDay());
            publisher.publishBookItemReservedEvent(bookItemId, userId, queuePosition, dueDate);
        }
        return request;
    }
}
