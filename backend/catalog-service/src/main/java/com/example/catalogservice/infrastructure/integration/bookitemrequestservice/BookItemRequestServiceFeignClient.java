package com.example.catalogservice.infrastructure.integration.bookitemrequestservice;

import com.example.catalogservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "request-service", path = "/book-requests", configuration = FeignClientCustomConfiguration.class)
interface BookItemRequestServiceFeignClient {

    @GetMapping("/{bookItemId}/isRequested")
    ResponseEntity<Boolean> isBookItemRequested(@PathVariable Long bookItemId);
}
