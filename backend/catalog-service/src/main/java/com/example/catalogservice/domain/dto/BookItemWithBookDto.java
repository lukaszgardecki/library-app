package com.example.catalogservice.domain.dto;

import com.example.catalogservice.domain.model.bookitem.values.BookItemStatus;
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
public class BookItemWithBookDto {
    private Long id;
    private String barcode;
    private Boolean isReferenceOnly;
    private LocalDate borrowed;
    private LocalDate dueDate;
    private BigDecimal price;
    private BookItemStatus status;
    private LocalDate dateOfPurchase;
    private BookDto book;
    private Long rackId;
    private String rackName;
    private Long shelfId;
    private String shelfName;
}
