package com.example.fineservice.domain.dto;

import com.example.fineservice.domain.model.FineStatus;
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
