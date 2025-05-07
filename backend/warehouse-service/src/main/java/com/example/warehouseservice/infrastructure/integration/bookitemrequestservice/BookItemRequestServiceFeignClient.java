package com.example.warehouseservice.infrastructure.integration.bookitemrequestservice;

import com.example.warehouseservice.domain.integration.request.dto.BookItemRequestDto;
import com.example.warehouseservice.domain.integration.request.BookItemRequestStatus;
import com.example.warehouseservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "request-service", path = "/book-requests", configuration = FeignClientCustomConfiguration.class)
interface BookItemRequestServiceFeignClient {

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

    @PatchMapping("/{requestId}")
    ResponseEntity<Void> changeBookRequestStatusToReady(@PathVariable Long requestId);
}
