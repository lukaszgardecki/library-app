package com.example.notificationservice.infrastructure.integration.catalogservice;

import com.example.notificationservice.domain.dto.BookDto;
import com.example.notificationservice.domain.model.BookItemId;
import com.example.notificationservice.domain.ports.CatalogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CatalogServiceAdapter implements CatalogServicePort {
    private final CatalogServiceFeignClient client;


    @Override
    public BookDto getBookByBookItemId(BookItemId bookItemId) {
        ResponseEntity<BookDto> response = client.getBookByBookItemId(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
