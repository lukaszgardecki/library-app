package com.example.libraryapp.NEWapplication.warehouse;

import com.example.libraryapp.NEWdomain.warehouse.ports.WarehouseEventListenerPort;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemRequestedEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.CustomEvent;
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
