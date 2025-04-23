package com.example.userservice.domain.ports;

import com.example.userservice.domain.model.user.UserId;

import java.util.List;

public interface StatisticsServicePort {

    List<Integer> countUserLoansPerMonth(UserId id);
}
