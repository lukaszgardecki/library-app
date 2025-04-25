package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.BookItemRequestStatus;
import com.example.requestservice.domain.model.RequestId;
import com.example.requestservice.domain.ports.BookItemRequestRepositoryPort;
import com.example.requestservice.domain.ports.EventListenerPort;
import com.example.requestservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
class EventListenerService implements EventListenerPort {
    private final BookItemRequestService bookItemRequestService;
    private final BookItemRequestRepositoryPort bookItemRequestRepository;
    private final EventPublisherPort publisher;


    @Override
    public void handleBookItemLoanedEvent(RequestId requestId) {
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
                        publisher.publishBookItemAvailableToLoan(req.getBookItemId(), req.getUserId());
                    }
                });
    }

    @Override
    public void handleBookItemLostEvent(BookItemId bookItemId) {
        cancelAllCurrentRequests(bookItemId);
    }

    @Override
    public void handleBookItemDeletedEvent(BookItemId bookItemId) {
        cancelAllCurrentRequests(bookItemId);
    }

    private void cancelAllCurrentRequests(BookItemId bookItemId) {
        List<BookItemRequestStatus> statusesToFind = bookItemRequestService.getCurrentRequestStatuses();
        bookItemRequestService.getAllByBookItemIdAndStatuses(bookItemId, statusesToFind)
                .forEach(request -> {
                    bookItemRequestService.cancelRequest(request.getId());
                    publisher.publishBookItemRequestCanceledEvent(request.getBookItemId(), request.getUserId());
                });
    }
}
