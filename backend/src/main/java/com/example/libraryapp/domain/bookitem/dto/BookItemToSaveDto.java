package com.example.libraryapp.domain.bookitem.dto;

import com.example.libraryapp.domain.bookitem.model.*;
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
    private BookItemFormat format;
    private LocalDate dateOfPurchase;
    private LocalDate publicationDate;
    private Long bookId;
    private Long rackId;

    public BookItem toModel() {
        return BookItem.builder()
                .isReferenceOnly(new IsReferenceOnly(isReferenceOnly))
                .price(new Price(price))
                .format(format)
                .dateOfPurchase(new PurchaseDate(dateOfPurchase))
                .publicationDate(new PublicationDate(publicationDate))
                .build();
    }
}
