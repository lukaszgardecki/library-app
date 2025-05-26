package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.model.user.values.UserId;

import java.util.Map;

public interface StatisticsServicePort {

    Map<Integer, Integer> countUserLoansPerMonth(UserId id);
}
