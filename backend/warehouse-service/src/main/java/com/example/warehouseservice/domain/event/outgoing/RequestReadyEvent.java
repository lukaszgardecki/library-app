package com.example.warehouseservice.domain.event.outgoing;

import com.example.warehouseservice.domain.model.BookItemId;
import com.example.warehouseservice.domain.model.RequestId;
import com.example.warehouseservice.domain.model.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequestReadyEvent {
    private final UserId userId;
    private final BookItemId bookItemId;
    private final RequestId requestId;
}