package com.example.requestservice.domain.ports.out;

import com.example.requestservice.domain.model.BookItemRequest;

public interface WebSocketSenderPort {

    void sendToWarehouse(BookItemRequest bookItemRequest);
}
