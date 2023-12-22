package com.example.libraryapp.domain.bookItem.dto;

import com.example.libraryapp.domain.bookItem.BookItemFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookItemToSaveDto {
    private Boolean isReferenceOnly;
    private BigDecimal price;
    private BookItemFormat format;
    private LocalDate dateOfPurchase;
    private LocalDate publicationDate;
    private Long bookId;
}
