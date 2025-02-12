package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.warehouse.ports.WarehouseEventListenerPort;
import com.example.libraryapp.domain.event.types.bookitem.BookItemRequestedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

@RequiredArgsConstructor
class WarehouseEventListenerImpl implements WarehouseEventListenerPort {
    // TODO: 10.12.2024 przenieść tego message poza, wystawić port i zrobić takiego w aplikacji
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return List.of(
                BookItemRequestedEvent.class
        );
    }

    @Override
    public void onEvent(CustomEvent event) {
        if (event instanceof BookItemRequestedEvent eventObj) {
            System.out.println("Wysyłam wiadomość do magazynu o zarezerwowaniu książki");
            // TODO: 03.12.2024 pomyśleć żeby ta ścieżka nie była wpisana na żywca tutaj tylko do jakiegoś configa...
            // TODO: 12.12.2024 poprawić to bo do magazynu trzerba wysłać dane potrzebne magazynowi
            messagingTemplate.convertAndSend("/queue/warehouse", eventObj.getBookItemId());
        }
    }
}
