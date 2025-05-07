package com.example.userservice.infrastructure.integration.bookitemloanservice;

import com.example.userservice.domain.integration.loan.dto.BookItemLoanDto;
import com.example.userservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "loan-service", path = "/loans", configuration = FeignClientCustomConfiguration.class)
interface BookItemLoanServiceFeignClient {

    @GetMapping("/all/list")
    ResponseEntity<List<BookItemLoanDto>> getAllLoansByUserId(@RequestParam("user_id") Long userId);

    @GetMapping("/current")
    ResponseEntity<List<BookItemLoanDto>> getCurrentLoansByUserId(@RequestParam("user_id") Long userId);

}
