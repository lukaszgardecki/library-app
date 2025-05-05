package com.example.notificationservice.infrastructure.integration.catalogservice;

import com.example.notificationservice.domain.dto.BookDto;
import com.example.notificationservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service", path = "/catalog", configuration = FeignClientCustomConfiguration.class)
interface CatalogServiceFeignClient {

    @GetMapping("/books/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable Long id);

    @GetMapping("/book-items/{bookItemId}/book")
    ResponseEntity<BookDto> getBookByBookItemId(@PathVariable Long bookItemId);
}
