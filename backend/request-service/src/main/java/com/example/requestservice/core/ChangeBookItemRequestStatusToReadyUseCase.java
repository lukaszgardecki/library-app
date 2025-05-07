package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.values.BookItemRequestStatus;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.domain.ports.out.BookItemRequestRepositoryPort;
import com.example.requestservice.domain.ports.out.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusToReadyUseCase {
    private final BookItemRequestService bookItemRequestService;
    private final BookItemRequestRepositoryPort bookItemRequestRepository;
    private final EventPublisherPort publisher;

    void execute(RequestId id) {
        bookItemRequestRepository.setBookRequestStatus(id, BookItemRequestStatus.READY);
        BookItemRequest bookItemRequest = bookItemRequestService.getBookItemRequestById(id);
        publisher.publishRequestReadyEvent(bookItemRequest.getBookItemId(), bookItemRequest.getUserId());
    }
}
