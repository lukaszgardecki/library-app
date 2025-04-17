package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.event.types.bookitem.BookItemRequestedEvent;
import com.example.libraryapp.domain.warehouse.model.WarehouseBookItemRequest;
import com.example.libraryapp.domain.warehouse.ports.WarehouseEventListenerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

@RequiredArgsConstructor
class WarehouseEventListenerAdapter implements WarehouseEventListenerPort {
    // TODO: 10.12.2024 przenieść tego message poza, wystawić port i zrobić takiego w aplikacji
    private final SimpMessagingTemplate messagingTemplate;
    private final BookItemRequestService bookItemRequestService;

    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return List.of(
                BookItemRequestedEvent.class
        );
    }

    @Override
    public void onEvent(CustomEvent event) {
        if (event instanceof BookItemRequestedEvent e) {
            WarehouseBookItemRequest warehouseBookItemRequest = bookItemRequestService.getWarehouseBookItemRequest(e.getBookItemRequest());
            messagingTemplate.convertAndSend(
                    "/queue/warehouse/pending",
                    BookItemRequestMapper.toRequestListViewDto(warehouseBookItemRequest)
            );
        }
    }
}
