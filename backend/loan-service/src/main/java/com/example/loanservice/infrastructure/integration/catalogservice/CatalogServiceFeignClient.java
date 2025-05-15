package com.example.loanservice.infrastructure.integration.catalogservice;

import com.example.loanservice.infrastructure.integration.FeignClientCustomConfiguration;
import com.example.loanservice.infrastructure.integration.catalogservice.dto.BookDto;
import com.example.loanservice.infrastructure.integration.catalogservice.dto.BookItemDto;
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

    @GetMapping("/{id}/verify/loan")
    ResponseEntity<BookItemDto> verifyAndGetBookItemForLoan(@PathVariable Long id);
}
