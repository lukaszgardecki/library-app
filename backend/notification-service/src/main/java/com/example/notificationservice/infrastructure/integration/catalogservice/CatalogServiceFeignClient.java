package com.example.notificationservice.infrastructure.integration.catalogservice;

import com.example.notificationservice.domain.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service", path = "/catalog")
public interface CatalogServiceFeignClient {

    @GetMapping("/book-items/{bookItemId}/book")
    ResponseEntity<BookDto> getBookByBookItemId(@PathVariable Long bookItemId);
}
