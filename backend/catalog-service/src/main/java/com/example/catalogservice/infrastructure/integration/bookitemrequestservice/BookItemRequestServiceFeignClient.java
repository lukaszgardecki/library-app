package com.example.catalogservice.infrastructure.integration.bookitemrequestservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-item-request", path = "/api/v1/book-requests")
public interface BookItemRequestServiceFeignClient {

    @GetMapping("/{bookItemId}/isRequested")
    ResponseEntity<Boolean> isBookItemRequested(@PathVariable Long bookItemId);
}
