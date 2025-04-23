package com.example.warehouseservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserDto {
    private Long id;
    private LocalDate registrationDate;
    private int totalBooksBorrowed;
    private int totalBooksReserved;
    private BigDecimal charge;
    private Long cardId;
    private Long personId;
}
