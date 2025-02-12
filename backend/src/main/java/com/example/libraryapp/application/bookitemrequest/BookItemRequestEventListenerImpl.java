package com.example.libraryapp.application.bookitemrequest;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestListenerPort;
import com.example.libraryapp.infrastructure.events.event.CustomEvent;
import com.example.libraryapp.infrastructure.events.event.bookitem.*;
import com.example.libraryapp.infrastructure.events.publishers.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
class BookItemRequestEventListenerImpl implements BookItemRequestListenerPort {
    private final BookItemRequestService bookItemRequestService;
    private final EventPublisherPort publisher;


    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return List.of(
                BookItemReturnedEvent.class,
                BookItemLostEvent.class
        );
    }

    @Override
    public void onEvent(CustomEvent event) {
        if (event instanceof BookItemReturnedEvent e) {
            handleBookItemReturnedEvent(e);
        } else if (event instanceof BookItemLostEvent e) {
            handleBookItemLostEvent(e);
        }
    }

    private void handleBookItemReturnedEvent(BookItemReturnedEvent event) {
        System.out.println("Zwróciłem książkę id: " + event.getBookItemId());
        List<BookItemRequestStatus> statusesToFind = List.of(
                BookItemRequestStatus.PENDING, BookItemRequestStatus.READY, BookItemRequestStatus.RESERVED
        );
        List<BookItemRequest> requests = bookItemRequestService.getAllByBookItemIdAndStatuses(event.getBookItemId(), statusesToFind);
        requests.stream()
                .sorted(Comparator.comparing(BookItemRequest::getCreationDate))
                .forEachOrdered(req -> {
                    if (requests.indexOf(req) == 0) {
                        publisher.publish(new BookItemAvailableToLoanEvent(req.getBookItemId(), req.getUserId(), event.getBookTitle()));
                    } else {
                        int queuePosition = requests.indexOf(req) + 1;
                        publisher.publish(new BookItemStillInQueueEvent(req.getBookItemId(), req.getUserId(), event.getBookTitle(), queuePosition));
                    }
                });
    }

    private void handleBookItemLostEvent(BookItemLostEvent event) {
        System.out.println("Zgubiono książkę: " + event.getBookTitle());
        List<BookItemRequestStatus> statusesToFind = List.of(
                BookItemRequestStatus.PENDING,
                BookItemRequestStatus.READY,
                BookItemRequestStatus.RESERVED
        );
        bookItemRequestService.getAllByBookItemIdAndStatuses(event.getBookItemId(), statusesToFind)
                .forEach(request -> {
                    bookItemRequestService.cancelRequest(request.getId());
                    publisher.publish(
                        new BookItemRequestCanceledEvent(request.getBookItemId(), request.getUserId(), event.getBookTitle())
                    );
                });
    }
}
