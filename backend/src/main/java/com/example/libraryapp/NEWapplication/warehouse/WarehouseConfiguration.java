package com.example.libraryapp.NEWapplication.warehouse;

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
    WarehouseEventListenerImpl warehouseEventListener(SimpMessagingTemplate messagingTemplate) {
        return new WarehouseEventListenerImpl(messagingTemplate);
    }
}
