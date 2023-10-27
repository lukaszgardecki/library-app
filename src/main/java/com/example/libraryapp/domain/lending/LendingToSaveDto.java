package com.example.libraryapp.domain.lending;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LendingToSaveDto {
    private Long userId;
    private Long bookId;
}