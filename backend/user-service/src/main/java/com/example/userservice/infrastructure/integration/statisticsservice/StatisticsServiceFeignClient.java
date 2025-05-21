package com.example.userservice.infrastructure.integration.statisticsservice;

import com.example.userservice.infrastructure.integration.FeignClientCustomConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@FeignClient(name = "statistics-service", path = "/stats", configuration = FeignClientCustomConfiguration.class)
interface StatisticsServiceFeignClient {

    @GetMapping("/users/{id}")
    ResponseEntity<Map<Integer, Integer>> countUserLoansPerMonth(@PathVariable Long id);
}
