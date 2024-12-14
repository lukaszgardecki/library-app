package com.example.libraryapp.NEWapplication.bookitemrequest;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import com.example.libraryapp.NEWapplication.book.BookFacade;
import com.example.libraryapp.NEWapplication.bookitem.BookItemFacade;
import com.example.libraryapp.NEWapplication.user.UserFacade;
import com.example.libraryapp.NEWdomain.bookitem.dto.BookItemDto;
import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemRequestedEvent;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
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
        BookItemDto bookItem = bookItemFacade.verifyAndGetBookItemForRequest(bookItemId);
        bookItemRequestService.verifyIfRequestExists(bookItemId, userId);
        BookItemRequest bookItemRequest = bookItemRequestService.saveRequest(bookItemId, userId);
        userFacade.updateUserAfterBookItemRequest(userId);
        bookItemFacade.updateBookItemAfterRequest(bookItemId);
        String bookTitle = bookFacade.getBook(bookItem.getBookId()).getTitle();
        publisher.publish(new BookItemRequestedEvent(bookItemId, userId, bookTitle));
        return bookItemRequest;
    }
}
