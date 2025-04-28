package com.example.userservice.infrastructure.integration.bookcatalogservice;

import com.example.userservice.domain.dto.user.BookDto;
import com.example.userservice.domain.dto.user.BookItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service", path = "/catalog")
interface BookCatalogServiceFeignClient {

    @GetMapping("/books/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable Long id);

    @GetMapping("/book-items/{id}")
    ResponseEntity<BookItemDto> getBookItem(@PathVariable Long id);
}
