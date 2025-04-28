package com.example.userservice.infrastructure.integration.statisticsservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "statistics-service", path = "/stats")
public interface StatisticsServiceFeignClient {

    @GetMapping("/users/{id}")
    ResponseEntity<List<Integer>> countUserLoansPerMonth(@PathVariable Long id);
}
