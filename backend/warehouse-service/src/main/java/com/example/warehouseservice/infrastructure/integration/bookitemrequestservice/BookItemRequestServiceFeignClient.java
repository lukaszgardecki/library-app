package com.example.warehouseservice.infrastructure.integration.bookitemrequestservice;

import com.example.warehouseservice.domain.dto.BookItemRequestDto;
import com.example.warehouseservice.domain.model.BookItemRequestStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "request-service", path = "/book-requests")
public interface BookItemRequestServiceFeignClient {

    @GetMapping
    ResponseEntity<Page<BookItemRequestDto>> getAllRequests(
            @RequestParam(required = false) BookItemRequestStatus status,
            Pageable pageable
    );

    @GetMapping("/{id}")
    ResponseEntity<BookItemRequestDto> getBookItemRequestById(@PathVariable Long id);

    @PatchMapping("/{requestId}/{status}")
    ResponseEntity<Void> changeBookItemRequestStatus(
            @PathVariable Long requestId,
            @PathVariable BookItemRequestStatus status
    );
}
