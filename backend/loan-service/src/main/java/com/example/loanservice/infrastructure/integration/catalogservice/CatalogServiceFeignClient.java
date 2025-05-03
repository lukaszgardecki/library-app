package com.example.loanservice.infrastructure.integration.catalogservice;

import com.example.loanservice.domain.dto.BookItemDto;
import com.example.loanservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-service", path = "/catalog", configuration = FeignClientCustomConfiguration.class)
interface CatalogServiceFeignClient {

    @GetMapping("/book-items/{id}")
    ResponseEntity<BookItemDto> getBookItemById(@PathVariable Long id);

    @GetMapping("/{id}/verify/loan")
    ResponseEntity<BookItemDto> verifyAndGetBookItemForLoan(@PathVariable Long id);
}
