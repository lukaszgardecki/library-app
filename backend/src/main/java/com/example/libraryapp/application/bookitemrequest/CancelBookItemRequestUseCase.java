package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.infrastructure.events.event.bookitem.BookItemRequestCanceledEvent;
import com.example.libraryapp.infrastructure.events.publishers.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CancelBookItemRequestUseCase {
    private final AuthenticationFacade authFacade;
    private final BookItemRequestService bookItemRequestService;
    private final BookItemFacade bookItemFacade;
    private final BookFacade bookFacade;
    private final EventPublisherPort publisher;

    void execute(Long bookItemId, Long userId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        BookItemRequest request = bookItemRequestService.getCurrentBookItemRequest(bookItemId, userId);
        bookItemRequestService.cancelRequest(request.getId());
        BookItemDto bookItem = bookItemFacade.getBookItem(bookItemId);
        String bookTitle = bookFacade.getBook(bookItem.getBookId()).getTitle();
        publisher.publish(new BookItemRequestCanceledEvent(request.getBookItemId(), request.getUserId(), bookTitle));
    }
}
