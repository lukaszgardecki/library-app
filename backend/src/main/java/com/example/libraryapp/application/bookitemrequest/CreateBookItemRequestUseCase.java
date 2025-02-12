package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.infrastructure.events.event.bookitem.BookItemRequestedEvent;
import com.example.libraryapp.infrastructure.events.event.bookitem.BookItemReservedEvent;
import com.example.libraryapp.infrastructure.events.publishers.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateBookItemRequestUseCase {
    private final UserFacade userFacade;
    private final AuthenticationFacade authFacade;
    private final BookItemRequestService bookItemRequestService;
    private final BookItemFacade bookItemFacade;
    private final BookFacade bookFacade;
    private final EventPublisherPort publisher;

    BookItemRequest execute(Long bookItemId, Long userId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        userFacade.verifyUserForBookItemRequest(userId);
        bookItemRequestService.verifyIfCurrentRequestExists(bookItemId, userId);

        BookItemDto bookItem = bookItemFacade.verifyAndGetBookItemForRequest(bookItemId);
        String bookTitle = bookFacade.getBook(bookItem.getBookId()).getTitle();

        BookItemRequest request;
        if (bookItem.getStatus() == BookItemStatus.AVAILABLE) {
            request = bookItemRequestService.saveRequest(bookItemId, userId);
            publisher.publish(new BookItemRequestedEvent(bookItemId, userId, bookTitle));
        } else {
            request = bookItemRequestService.saveReservation(bookItemId, userId);
            int queuePosition = bookItemRequestService.getReservationQueuePosition(bookItemId, userId);
            publisher.publish(new BookItemReservedEvent(bookItemId, userId, bookTitle, queuePosition));
        }
        return request;
    }
}
