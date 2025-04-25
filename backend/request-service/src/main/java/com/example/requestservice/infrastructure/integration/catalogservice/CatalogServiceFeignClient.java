package com.example.requestservice.infrastructure.integration.catalogservice;

import com.example.requestservice.domain.dto.BookItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service", path = "/api/v1")
public interface CatalogServiceFeignClient {

    @GetMapping("/{id}/verify/request")
    ResponseEntity<BookItemDto> verifyAndGetBookItemForRequest(@PathVariable Long id);
}
