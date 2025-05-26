package com.example.userservice.infrastructure.integration.bookcatalogservice;

import com.example.userservice.domain.integration.catalog.BookItemId;
import com.example.userservice.domain.integration.catalog.book.Book;
import com.example.userservice.domain.ports.out.CatalogServicePort;
import com.example.userservice.infrastructure.integration.bookcatalogservice.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CatalogServiceAdapter implements CatalogServicePort {
    private final BookCatalogServiceFeignClient client;

    @Override
    public Book getBookByBookItemId(BookItemId bookItemId) {
        ResponseEntity<BookDto> response = client.getBookByBookItemId(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return BookMapper.toModel(response.getBody());
        }
        return null;
    }
}
