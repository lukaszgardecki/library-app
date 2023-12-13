package com.example.libraryapp.domain.bookItem.dto;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.bookItem.BookItemFormat;
import com.example.libraryapp.domain.bookItem.BookItemStatus;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookItemDto extends RepresentationModel<BookItemDto> {
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
    private BookDto book;
}
