package com.example.libraryapp.domain.fine.dto;

import com.example.libraryapp.domain.fine.model.FineStatus;
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
    private FineStatus status;
}
