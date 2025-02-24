package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestListenerPort;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.event.types.bookitem.*;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
class BookItemRequestEventListenerAdapter implements BookItemRequestListenerPort {
    private final BookItemRequestService bookItemRequestService;
    private final EventPublisherPort publisher;

    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return List.of(
                BookItemReturnedEvent.class,
                BookItemLostEvent.class,
                BookItemDeletedEvent.class
        );
    }

    @Override
    public void onEvent(CustomEvent event) {
        if (event instanceof BookItemReturnedEvent e) {
            notifyUsersAboutReturnedBook(e);
        } else if (event instanceof BookItemLostEvent e) {
            cancelAllCurrentRequests(e.getBookItemId(), e.getBookTitle());
        } else if (event instanceof BookItemDeletedEvent e) {
            cancelAllCurrentRequests(e.getBookItemId(), e.getBookTitle());
        }
    }

    private void notifyUsersAboutReturnedBook(BookItemReturnedEvent event) {
        List<BookItemRequestStatus> statusesToFind = bookItemRequestService.getCurrentRequestStatuses();
        List<BookItemRequest> requests = bookItemRequestService.getAllByBookItemIdAndStatuses(event.getBookItemId(), statusesToFind);
        requests.stream()
                .sorted(Comparator.comparing(BookItemRequest::getCreationDate))
                .forEachOrdered(req -> {
                    if (requests.indexOf(req) == 0) {
                        publisher.publish(new BookItemAvailableToLoanEvent(req.getBookItemId(), req.getUserId(), event.getBookTitle()));
                    }
                });
    }

    private void cancelAllCurrentRequests(Long bookItemId, String bookTitle) {
        List<BookItemRequestStatus> statusesToFind = bookItemRequestService.getCurrentRequestStatuses();
        bookItemRequestService.getAllByBookItemIdAndStatuses(bookItemId, statusesToFind)
                .forEach(request -> {
                    bookItemRequestService.cancelRequest(request.getId());
                    publisher.publish(
                        new BookItemRequestCanceledEvent(request.getBookItemId(), request.getUserId(), bookTitle)
                    );
                });
    }
}
