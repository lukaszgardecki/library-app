package com.example.libraryapp.NEWdomain.bookitem.dto;

import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItemFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookItemToSaveDto {
    private Boolean isReferenceOnly;
    private BigDecimal price;
    private BookItemFormat format;
    private LocalDate dateOfPurchase;
    private LocalDate publicationDate;
    private Long bookId;
    private Long rackId;

    public BookItem toModel() {
        return BookItem.builder()
                .isReferenceOnly(isReferenceOnly)
                .price(price)
                .format(format)
                .dateOfPurchase(dateOfPurchase)
                .publicationDate(publicationDate)
                .build();
    }
}
