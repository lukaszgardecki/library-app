package com.example.requestservice.infrastructure.integration.catalogservice;


import com.example.requestservice.domain.dto.BookItemDto;
import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.ports.CatalogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CatalogServiceAdapter implements CatalogServicePort {
    private final CatalogServiceFeignClient client;

    @Override
    public BookItemDto verifyAndGetBookItemForRequest(BookItemId bookItemId) {
        ResponseEntity<BookItemDto> response = client.verifyAndGetBookItemForRequest(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
