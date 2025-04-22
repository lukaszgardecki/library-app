package com.example.catalogservice.bookitem.domain.dto;

import com.example.catalogservice.bookitem.domain.model.BookItem;
import com.example.catalogservice.bookitem.domain.model.IsReferenceOnly;
import com.example.catalogservice.bookitem.domain.model.Price;
import com.example.catalogservice.bookitem.domain.model.PurchaseDate;
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

    public BookItem toModel() {
        return BookItem.builder()
                .isReferenceOnly(new IsReferenceOnly(isReferenceOnly))
                .price(new Price(price))
                .dateOfPurchase(new PurchaseDate(dateOfPurchase))
                .build();
    }
}
