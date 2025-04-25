package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.BookItemRequestStatus;
import com.example.requestservice.domain.model.RequestId;
import com.example.requestservice.domain.ports.BookItemRequestRepositoryPort;
import com.example.requestservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusToReadyUseCase {
    private final BookItemRequestService bookItemRequestService;
    private final BookItemRequestRepositoryPort bookItemRequestRepository;
    private final EventPublisherPort publisher;

    void execute(RequestId id) {
        bookItemRequestRepository.setBookRequestStatus(id, BookItemRequestStatus.READY);
        BookItemRequest bookItemRequest = bookItemRequestService.getCurrentBookItemRequestById(id);
        publisher.publishBookItemRequestReadyEvent(bookItemRequest.getBookItemId(), bookItemRequest.getUserId());
    }
}
