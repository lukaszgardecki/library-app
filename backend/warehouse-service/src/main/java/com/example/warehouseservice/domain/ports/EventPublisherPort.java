package com.example.warehouseservice.domain.ports;

import com.example.warehouseservice.domain.model.RequestId;

public interface EventPublisherPort {

    void publishBookItemRequestReadyEvent(RequestId requestId);
}
