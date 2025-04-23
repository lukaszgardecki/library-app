package com.example.userservice.infrastructure.integration.bookcatalogservice;

import com.example.userservice.domain.dto.user.BookDto;
import com.example.userservice.domain.dto.user.BookItemDto;
import com.example.userservice.domain.model.book.BookId;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.ports.BookCatalogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BookCatalogServiceAdapter implements BookCatalogServicePort {
    private final BookCatalogServiceFeignClient client;

    @Override
    public BookItemDto getBookItem(BookItemId bookItemId) {
        ResponseEntity<BookItemDto> response = client.getBookItem(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public BookDto getBook(BookId bookId) {
        ResponseEntity<BookDto> response = client.getBookById(bookId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
