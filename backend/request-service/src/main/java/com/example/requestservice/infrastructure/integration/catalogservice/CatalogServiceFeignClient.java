package com.example.requestservice.infrastructure.integration.catalogservice;

import com.example.requestservice.domain.integration.catalog.dto.BookDto;
import com.example.requestservice.domain.integration.catalog.dto.BookItemDto;
import com.example.requestservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service", path = "/catalog", configuration = FeignClientCustomConfiguration.class)
interface CatalogServiceFeignClient {

    @GetMapping("/book-items/{id}")
    ResponseEntity<BookItemDto> getBookItemById(@PathVariable Long id);

    @GetMapping("/book-items/{bookItemId}/book")
    ResponseEntity<BookDto> getBookByBookItemId(@PathVariable Long bookItemId);

    @GetMapping("/book-items/{bookItemId}/book/id")
    ResponseEntity<Long> getBookIdByBookItemId(@PathVariable Long bookItemId);

    @GetMapping("/book-items/{id}/verify/request")
    ResponseEntity<BookItemDto> verifyAndGetBookItemForRequest(@PathVariable Long id);

    @GetMapping("/books/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable Long id);
}
