package com.example.warehouseservice.core.bookitemrequest;

import com.example.warehouseservice.domain.model.RequestId;
import com.example.warehouseservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ChangeBookItemRequestStatusToReadyUseCase {
    private final EventPublisherPort publisher;

    void execute(RequestId requestId) {
        publisher.publishBookItemRequestReadyEvent(requestId);
    }
}
