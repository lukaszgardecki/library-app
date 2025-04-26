package com.example.warehouseservice.core.bookitemrequest;

import com.example.warehouseservice.domain.dto.BookItemRequestDto;
import com.example.warehouseservice.domain.model.BookItemId;
import com.example.warehouseservice.domain.model.RequestId;
import com.example.warehouseservice.domain.model.UserId;
import com.example.warehouseservice.domain.ports.BookItemRequestServicePort;
import com.example.warehouseservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusToReadyUseCase {
    private final BookItemRequestServicePort bookItemRequestService;
    private final EventPublisherPort publisher;

    void execute(RequestId requestId) {
        BookItemRequestDto request = bookItemRequestService.getBookItemRequestById(requestId);
        publisher.publishBookItemRequestReadyEvent(new UserId(request.getUserId()), new BookItemId(request.getBookItemId()), requestId);
    }
}
