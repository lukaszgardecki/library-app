package com.example.libraryapp.application.warehouse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Configuration
class WarehouseConfiguration {

    @Bean
    WarehouseFacade warehouseFacade() {
        return new WarehouseFacade();
    }

    @Bean
    WarehouseEventListenerAdapter warehouseEventListener(SimpMessagingTemplate messagingTemplate) {
        return new WarehouseEventListenerAdapter(messagingTemplate);
    }
}
