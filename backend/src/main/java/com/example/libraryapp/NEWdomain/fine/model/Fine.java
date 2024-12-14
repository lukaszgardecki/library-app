package com.example.libraryapp.NEWdomain.fine.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Fine {
    private Long id;
    private Long userId;
    private Long loanId;
    private BigDecimal amount;
    private Boolean paid;
}
