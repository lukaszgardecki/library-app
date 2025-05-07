package com.example.warehouseservice.infrastructure.integration.catalogservice;

import com.example.warehouseservice.domain.integration.catalog.dto.BookDto;
import com.example.warehouseservice.domain.integration.catalog.dto.BookItemDto;
import com.example.warehouseservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "catalog-service", path = "/catalog", configuration = FeignClientCustomConfiguration.class)
interface CatalogServiceFeignClient {

    @GetMapping("/book-items/{id}")
    ResponseEntity<BookItemDto> getBookItemById(@PathVariable Long id);

    @GetMapping("/book-items/count")
    ResponseEntity<Long> countByParams(
            @RequestParam(name = "rack_id", required = false) Long rackId,
            @RequestParam(name = "shelf_id", required = false) Long shelfId
    );

    @GetMapping("/books/{id}")
    ResponseEntity<BookDto> getBookById(@PathVariable Long id);
}
