package com.example.warehouseservice.infrastructure.integration.catalogservice;

import com.example.warehouseservice.domain.dto.BookDto;
import com.example.warehouseservice.domain.dto.BookItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "catalog-service", path = "/api/v1")
public interface CatalogServiceFeignClient {

    @GetMapping("/book-items/{id}")
    ResponseEntity<BookItemDto> getBookItemById(@PathVariable Long id);

    @GetMapping("/book-items/count")
    ResponseEntity<Long> countByParams(
            @RequestParam(value = "rack_id", required = false) Long rackId,
            @RequestParam(value = "shelf_id", required = false) Long shelfId
    );

    @GetMapping("/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable Long id);
}
