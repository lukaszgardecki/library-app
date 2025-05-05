package com.example.requestservice.infrastructure.integration.catalogservice;

import com.example.requestservice.domain.dto.BookItemDto;
import com.example.requestservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service", path = "/catalog", configuration = FeignClientCustomConfiguration.class)
interface CatalogServiceFeignClient {

    @GetMapping("/{bookItemId}/book/id")
    ResponseEntity<Long> getBookIdByBookItemId(@PathVariable Long bookItemId);

    @GetMapping("/{id}/verify/request")
    ResponseEntity<BookItemDto> verifyAndGetBookItemForRequest(@PathVariable Long id);
}
