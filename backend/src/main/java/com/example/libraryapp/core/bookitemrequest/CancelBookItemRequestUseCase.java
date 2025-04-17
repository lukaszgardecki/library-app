package com.example.libraryapp.core.bookitemrequest;

import com.example.libraryapp.core.auth.AuthenticationFacade;
import com.example.libraryapp.core.book.BookFacade;
import com.example.libraryapp.core.bookitem.BookItemFacade;
import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.event.types.bookitem.BookItemRequestCanceledEvent;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CancelBookItemRequestUseCase {
    private final AuthenticationFacade authFacade;
    private final BookItemRequestService bookItemRequestService;
    private final BookItemFacade bookItemFacade;
    private final BookFacade bookFacade;
    private final EventPublisherPort publisher;

    void execute(BookItemId bookItemId, UserId userId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        BookItemRequest request = bookItemRequestService.getCurrentBookItemRequest(bookItemId, userId);
        bookItemRequestService.cancelRequest(request.getId());
        BookItemDto bookItem = bookItemFacade.getBookItem(bookItemId);
        String bookTitle = bookFacade.getBook(new BookId(bookItem.getBookId())).getTitle();
        publisher.publish(new BookItemRequestCanceledEvent(request.getBookItemId(), request.getUserId(), new Title(bookTitle)));
    }
}
