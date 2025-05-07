package com.example.loanservice.domain.integration.catalog.dto;

import com.example.loanservice.domain.integration.catalog.BookItemStatus;
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
public class BookItemDto {
    private Long id;
    private String barcode;
    private Boolean isReferenceOnly;
    private LocalDate borrowed;
    private LocalDate dueDate;
    private BigDecimal price;
    private BookItemStatus status;
    private LocalDate dateOfPurchase;
    private Long bookId;
    private Long rackId;
    private Long shelfId;
}
