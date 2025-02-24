package com.example.libraryapp.domain.bookitem.dto;

import com.example.libraryapp.domain.bookitem.model.BookItemFormat;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import lombok.*;

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
    private BookItemFormat format;
    private BookItemStatus status;
    private LocalDate dateOfPurchase;
    private LocalDate publicationDate;
    private Long bookId;
    private Long rackId;
}
