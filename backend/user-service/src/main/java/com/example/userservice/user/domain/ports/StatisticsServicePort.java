package com.example.userservice.user.domain.ports;

import com.example.userservice.user.domain.model.user.UserId;

import java.util.List;

public interface StatisticsServicePort {

    List<Integer> countUserLoansPerMonth(UserId id);
}
