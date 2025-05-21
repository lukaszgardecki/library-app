package com.example.catalogservice.infrastructure.http.dto;

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
public class BookItemToSaveDto {
    private Boolean isReferenceOnly;
    private BigDecimal price;
    private LocalDate dateOfPurchase;
    private Long bookId;
    private Long rackId;
    private Long shelfId;
}
