package com.example.statisticsservice.domain.model.usermonthlyloans;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMonthlyLoans {
    private Long id;
    private Long userId;
    private int yearValue;
    private int monthValue;
    private int loansCount;
}
