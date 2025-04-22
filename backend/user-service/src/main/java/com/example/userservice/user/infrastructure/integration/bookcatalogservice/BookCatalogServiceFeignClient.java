package com.example.userservice.user.infrastructure.integration.bookcatalogservice;

import com.example.userservice.user.domain.dto.BookDto;
import com.example.userservice.user.domain.dto.BookItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "old-common", path = "/api/v1")
interface BookCatalogServiceFeignClient {

    @GetMapping("/books/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable Long id);

    @GetMapping("/book-items/{id}")
    ResponseEntity<BookItemDto> getBookItem(@PathVariable Long id);
}
