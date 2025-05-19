package com.example.userservice.infrastructure.integration.statisticsservice;

import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.domain.ports.out.StatisticsServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
class StatisticsServiceAdapter implements StatisticsServicePort {
    private final StatisticsServiceFeignClient client;

    @Override
    public Map<Integer, Integer> countUserLoansPerMonth(UserId id) {
        ResponseEntity<Map<Integer, Integer>> response = client.countUserLoansPerMonth(id.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
