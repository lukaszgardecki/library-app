package com.example.libraryapp.domain.bookItem.dto;

import com.example.libraryapp.domain.bookItem.BookItemFormat;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookItemToSaveDto {
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
}
