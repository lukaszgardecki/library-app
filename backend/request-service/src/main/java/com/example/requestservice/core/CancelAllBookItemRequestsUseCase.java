package com.example.requestservice.core;

import com.example.requestservice.domain.integration.catalog.BookId;
import com.example.requestservice.domain.model.values.UserId;
import com.example.requestservice.domain.ports.out.CatalogServicePort;
import com.example.requestservice.domain.ports.out.EventPublisherPort;
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
