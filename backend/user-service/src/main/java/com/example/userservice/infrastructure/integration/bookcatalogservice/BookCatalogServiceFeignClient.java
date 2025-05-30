package com.example.userservice.infrastructure.integration.bookcatalogservice;

import com.example.userservice.infrastructure.integration.FeignClientCustomConfiguration;
import com.example.userservice.infrastructure.integration.bookcatalogservice.dto.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service", path = "/catalog", configuration = FeignClientCustomConfiguration.class)
interface BookCatalogServiceFeignClient {

    @GetMapping("/book-items/{bookItemId}/book")
    ResponseEntity<BookDto> getBookByBookItemId(@PathVariable Long bookItemId);
}
