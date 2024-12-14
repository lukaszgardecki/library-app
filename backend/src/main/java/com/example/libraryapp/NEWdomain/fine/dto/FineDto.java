package com.example.libraryapp.NEWdomain.fine.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FineDto {
    private Long id;
    private Long userId;
    private Long loanId;
    private BigDecimal amount;
    private Boolean paid;
}
