package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.application.book.BookFacade;
import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.application.user.UserFacade;
import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.dto.BookItemDto;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.event.types.bookitem.BookItemRequestedEvent;
import com.example.libraryapp.domain.event.types.bookitem.BookItemReservedEvent;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
class CreateBookItemRequestUseCase {
    private final UserFacade userFacade;
    private final AuthenticationFacade authFacade;
    private final BookItemRequestService bookItemRequestService;
    private final BookItemFacade bookItemFacade;
    private final BookFacade bookFacade;
    private final EventPublisherPort publisher;

    BookItemRequest execute(BookItemId bookItemId, UserId userId) {
        authFacade.validateOwnerOrAdminAccess(userId);
        userFacade.verifyUserForBookItemRequest(userId);
        bookItemRequestService.verifyIfCurrentRequestExists(bookItemId, userId);

        BookItemDto bookItem = bookItemFacade.verifyAndGetBookItemForRequest(bookItemId);
        Title bookTitle = new Title(bookFacade.getBook(new BookId(bookItem.getBookId())).getTitle());

        BookItemRequest request;
        if (bookItem.getStatus() == BookItemStatus.AVAILABLE) {
            request = bookItemRequestService.saveRequest(bookItemId, userId);
            publisher.publish(new BookItemRequestedEvent(BookItemRequestMapper.toDto(request), bookTitle));
        } else {
            request = bookItemRequestService.saveReservation(bookItemId, userId);
            int queuePosition = bookItemRequestService.getReservationQueuePosition(bookItemId, userId);
            LoanDueDate dueDate = new LoanDueDate(bookItem.getDueDate().atStartOfDay());
            publisher.publish(new BookItemReservedEvent(bookItemId, userId, bookTitle, queuePosition, dueDate));
        }
        return request;
    }
}
