package com.example.catalogservice.infrastructure.integration.bookitemrequestservice;

import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.ports.out.BookItemRequestServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BookItemRequestServiceAdapter implements BookItemRequestServicePort {
    private final BookItemRequestServiceFeignClient client;

    @Override
    public Boolean isBookItemRequested(BookItemId bookItemId) {
        ResponseEntity<Boolean> response = client.isBookItemRequested(bookItemId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
