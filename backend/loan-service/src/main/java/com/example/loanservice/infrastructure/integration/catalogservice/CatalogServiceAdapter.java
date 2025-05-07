package com.example.loanservice.infrastructure.integration.catalogservice;

import com.example.loanservice.domain.integration.catalog.dto.BookDto;
import com.example.loanservice.domain.integration.catalog.dto.BookItemDto;
import com.example.loanservice.domain.integration.catalog.BookId;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.ports.out.CatalogServicePort;
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
    public BookDto getBookByBookItemId(BookItemId bookItemId) {
        ResponseEntity<BookDto> response = client.getBookByBookItemId(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public BookId getBookIdByBookItemId(BookItemId bookItemId) {
        ResponseEntity<Long> reponse = client.getBookIdByBookItemId(bookItemId.value());
        if (reponse.getStatusCode().is2xxSuccessful()) {
            return new BookId(reponse.getBody());
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
