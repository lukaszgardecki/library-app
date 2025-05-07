package com.example.userservice.infrastructure.integration.bookcatalogservice;

import com.example.userservice.domain.integration.catalog.dto.BookDto;
import com.example.userservice.domain.integration.catalog.BookItemId;
import com.example.userservice.domain.ports.out.CatalogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CatalogServiceAdapter implements CatalogServicePort {
    private final BookCatalogServiceFeignClient client;

    @Override
    public BookDto getBookByBookItemId(BookItemId bookItemId) {
        ResponseEntity<BookDto> response = client.getBookByBookItemId(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
