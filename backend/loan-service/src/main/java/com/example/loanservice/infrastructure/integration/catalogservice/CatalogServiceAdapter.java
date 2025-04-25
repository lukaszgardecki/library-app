package com.example.loanservice.infrastructure.integration.catalogservice;

import com.example.loanservice.domain.dto.BookItemDto;
import com.example.loanservice.domain.model.BookItemId;
import com.example.loanservice.domain.ports.CatalogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CatalogServiceAdapter implements CatalogServicePort {
    private final CatalogServiceFeignClient client;

    @Override
    public BookItemDto getBookItemById(BookItemId bookItemId) {
        ResponseEntity<BookItemDto> response = client.getBookItemById(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public BookItemDto verifyAndGetBookItemForLoan(BookItemId bookItemId) {
        ResponseEntity<BookItemDto> response = client.verifyAndGetBookItemForLoan(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
