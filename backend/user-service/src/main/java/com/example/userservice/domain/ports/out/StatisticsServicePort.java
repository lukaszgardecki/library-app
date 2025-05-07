package com.example.userservice.domain.ports.out;

import com.example.userservice.domain.model.user.values.UserId;

import java.util.List;

public interface StatisticsServicePort {

    List<Integer> countUserLoansPerMonth(UserId id);
}
