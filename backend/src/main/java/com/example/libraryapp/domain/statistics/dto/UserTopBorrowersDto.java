package com.example.libraryapp.domain.statistics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTopBorrowersDto {
    private Long id;
    private int rank;
    private String fullName;
    private int totalBooksBorrowed;
}
