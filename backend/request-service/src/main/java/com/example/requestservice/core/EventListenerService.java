package com.example.requestservice.core;

import com.example.requestservice.domain.integration.catalog.BookId;
import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.BookItemRequestStatus;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.domain.ports.in.EventListenerPort;
import com.example.requestservice.domain.ports.out.BookItemRequestRepositoryPort;
import com.example.requestservice.domain.ports.out.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
class EventListenerService implements EventListenerPort {
    private final BookItemRequestService bookItemRequestService;
    private final BookItemRequestRepositoryPort bookItemRequestRepository;
    private final EventPublisherPort publisher;

    @Override
    public void handleBookItemDeletedEvent(BookItemId bookItemId, BookId bookId) {
        cancelAllCurrentRequests(bookItemId, bookId);
    }

    @Override
    public void handleLoanCreatedEvent(RequestId requestId) {
        bookItemRequestRepository.setBookRequestStatus(requestId, BookItemRequestStatus.COMPLETED);
    }

    @Override
    public void handleBookItemReturnedEvent(BookItemId bookItemId) {
        List<BookItemRequestStatus> statusesToFind = bookItemRequestService.getCurrentRequestStatuses();
        List<BookItemRequest> requests = bookItemRequestService.getAllByBookItemIdAndStatuses(bookItemId, statusesToFind);
        requests.stream()
                .sorted(Comparator.comparing(request -> request.getCreationDate().value()))
                .forEachOrdered(req -> {
                    if (requests.indexOf(req) == 0) {
                        publisher.publishRequestAvailableToLoanEvent(req.getBookItemId(), req.getUserId());
                    }
                });
    }

    @Override
    public void handleBookItemLostEvent(BookItemId bookItemId, BookId bookId) {
        cancelAllCurrentRequests(bookItemId, bookId);
    }

    private void cancelAllCurrentRequests(BookItemId bookItemId, BookId bookId) {
        List<BookItemRequestStatus> statusesToFind = bookItemRequestService.getCurrentRequestStatuses();
        bookItemRequestService.getAllByBookItemIdAndStatuses(bookItemId, statusesToFind)
                .forEach(request -> {
                    bookItemRequestService.cancelRequest(request.getId());
                    publisher.publishRequestCanceledEvent(request.getBookItemId(), request.getUserId(), bookId);
                });
    }
}
