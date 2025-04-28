package com.example.loanservice.infrastructure.integration.bookitemrequestservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "request-service", path = "/book-requests")
public interface BookItemRequestServiceFeignClient {

    @GetMapping("/{bookItemId}/isRequested")
    ResponseEntity<Boolean> isBookItemRequested(@PathVariable Long bookItemId);

    @GetMapping("/book-item/{bookItemId}/check-not-requested")
    ResponseEntity<Void> ensureBookItemNotRequested(@PathVariable Long bookItemId);

    @GetMapping("/book-item/{bookItemId}/user/{userId}/ready")
    ResponseEntity<Long> checkIfBookItemRequestStatusIsReady(
            @PathVariable Long bookItemId,
            @PathVariable Long userId
    );
}
