package com.example.requestservice.infrastructure.websockets;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.ports.out.WebSocketSenderPort;
import com.example.requestservice.infrastructure.http.BookItemRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WebSocketSenderAdapter implements WebSocketSenderPort {
    private final SimpMessagingTemplate messagingTemplate;
    private final BookItemRequestMapper mapper;

    @Override
    public void sendToWarehouse(BookItemRequest bookItemRequest) {
        messagingTemplate.convertAndSend("/queue/warehouse/pending", mapper.toDto(bookItemRequest));
    }
}
