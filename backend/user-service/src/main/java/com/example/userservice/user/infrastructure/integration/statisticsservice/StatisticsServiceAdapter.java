package com.example.userservice.user.infrastructure.integration.statisticsservice;

import com.example.userservice.user.domain.model.user.UserId;
import com.example.userservice.user.domain.ports.StatisticsServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class StatisticsServiceAdapter implements StatisticsServicePort {
    private final StatisticsServiceFeignClient client;

    @Override
    public List<Integer> countUserLoansPerMonth(UserId id) {
        ResponseEntity<List<Integer>> response = client.countUserLoansPerMonth(id.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
