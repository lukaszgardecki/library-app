package com.example.userservice.infrastructure.integration.bookitemrequestservice;

import com.example.userservice.infrastructure.integration.FeignClientCustomConfiguration;
import com.example.userservice.infrastructure.integration.bookitemrequestservice.dto.BookItemRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "request-service", path = "/book-requests", configuration = FeignClientCustomConfiguration.class)
interface BookItemRequestServiceFeignClient {

    @DeleteMapping("/users/{userId}/cancel-all")
    ResponseEntity<Void> cancelAllItemRequestsByUserId(@PathVariable Long userId);

    @GetMapping("/current")
    ResponseEntity<List<BookItemRequestDto>> getUserCurrentBookItemRequests(@RequestParam("userId") Long id);
}
