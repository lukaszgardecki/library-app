package com.example.requestservice.core;

import com.example.requestservice.domain.model.BookId;
import com.example.requestservice.domain.model.UserId;
import com.example.requestservice.domain.ports.CatalogServicePort;
import com.example.requestservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CancelAllBookItemRequestsUseCase {
    private final BookItemRequestService bookItemRequestService;
    private final CatalogServicePort catalogServicePort;
    private final EventPublisherPort publisher;

    void execute(UserId userId) {
        bookItemRequestService.getAllCurrentBookItemRequestsByUserId(userId)
                .forEach(request -> {
                    BookId bookId = catalogServicePort.getBookIdByBookItemId(request.getBookItemId());
                    bookItemRequestService.cancelRequest(request.getId());
                    publisher.publishRequestCanceledEvent(request.getBookItemId(), request.getUserId(), bookId);
                });
    }
}
