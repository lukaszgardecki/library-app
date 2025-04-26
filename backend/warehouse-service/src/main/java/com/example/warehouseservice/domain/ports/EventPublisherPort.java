package com.example.warehouseservice.domain.ports;

import com.example.warehouseservice.domain.model.BookItemId;
import com.example.warehouseservice.domain.model.RequestId;
import com.example.warehouseservice.domain.model.UserId;

public interface EventPublisherPort {

    void publishBookItemRequestReadyEvent(UserId userId, BookItemId bookItemId, RequestId requestId);
}
