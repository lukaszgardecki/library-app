package com.example.libraryapp.domain.bookitem.dto;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
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
    private Long shelfId;
}
