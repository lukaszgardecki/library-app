package com.example.warehouseservice.infrastructure.integration.bookitemrequestservice;

import com.example.warehouseservice.domain.dto.BookItemRequestDto;
import com.example.warehouseservice.domain.model.BookItemRequestStatus;
import com.example.warehouseservice.domain.model.RequestId;
import com.example.warehouseservice.domain.ports.BookItemRequestServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class BookItemRequestServiceAdapter implements BookItemRequestServicePort {
    private final BookItemRequestServiceFeignClient client;

    @Override
    public Page<BookItemRequestDto> getPageOfBookRequestsByStatus(BookItemRequestStatus status, Pageable pageable) {
        ResponseEntity<Page<BookItemRequestDto>> response = client.getAllRequests(status, pageable);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public BookItemRequestDto getBookItemRequestById(RequestId requestId) {
        ResponseEntity<BookItemRequestDto> response = client.getBookItemRequestById(requestId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public void changeBookItemRequestStatus(RequestId requestId, BookItemRequestStatus status) {
        ResponseEntity<Void> response = client.changeBookItemRequestStatus(requestId.value(), status);
        if (response.getStatusCode().is2xxSuccessful()) {
            // TODO: 07.05.2025 jeśli OK
        }
        // TODO: 07.05.2025 jeśli nie OK
    }

    @Override
    public void changeBookRequestStatusToReady(RequestId requestId) {
        ResponseEntity<Void> response = client.changeBookRequestStatusToReady(requestId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            // TODO: 07.05.2025 jeśli OK
        }
        // TODO: 07.05.2025 jeśli nie OK
    }
}
