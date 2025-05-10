package com.example.statisticsservice.infrastructure.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BorrowerDto {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private int loans;
}
