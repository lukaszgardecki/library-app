package com.example.warehouseservice.infrastructure.integration.catalogservice;

import com.example.warehouseservice.domain.dto.BookDto;
import com.example.warehouseservice.domain.dto.BookItemDto;
import com.example.warehouseservice.domain.model.BookId;
import com.example.warehouseservice.domain.model.BookItemId;
import com.example.warehouseservice.domain.model.rack.RackId;
import com.example.warehouseservice.domain.model.shelf.ShelfId;
import com.example.warehouseservice.domain.ports.CatalogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CatalogServiceAdapter implements CatalogServicePort {
    private final CatalogServiceFeignClient client;

    @Override
    public Long countBookItemsByParams(RackId rackId, ShelfId shelfId) {
        ResponseEntity<Long> response = client.countByParams(rackId.value(), shelfId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public BookItemDto getBookItemById(BookItemId bookItemId) {
        ResponseEntity<BookItemDto> response = client.getBookItemById(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public BookDto getBookById(BookId bookId) {
        ResponseEntity<BookDto> response = client.getBookById(bookId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
