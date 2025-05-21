package com.example.statisticsservice.core;

import com.example.statisticsservice.domain.model.borrower.values.UserId;
import com.example.statisticsservice.domain.model.usermonthlyloans.UserMonthlyLoans;
import com.example.statisticsservice.domain.ports.out.UserMonthlyLoansRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class CountMonthlyUserLoansByDateUseCase {
    private final UserMonthlyLoansRepositoryPort userMonthlyLoansRepository;

    Map<Integer, Integer> execute(UserId userId, LocalDate start, LocalDate end) {
        return userMonthlyLoansRepository.findAllByUserIdAndDateBetween(userId.value(), start, end).stream()
                .collect(Collectors.toMap(
                        UserMonthlyLoans::getMonthValue,
                        UserMonthlyLoans::getLoansCount
                ));
    }
}
