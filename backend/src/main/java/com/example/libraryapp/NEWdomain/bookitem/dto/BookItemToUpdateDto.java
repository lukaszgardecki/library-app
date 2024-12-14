package com.example.libraryapp.NEWdomain.bookitem.dto;

import com.example.libraryapp.NEWdomain.bookitem.model.BookItem;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItemFormat;
import com.example.libraryapp.NEWdomain.bookitem.model.BookItemStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookItemToUpdateDto {
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

    public BookItem toModel() {
        return BookItem.builder()
                .isReferenceOnly(isReferenceOnly)
                .borrowed(borrowed)
                .dueDate(dueDate)
                .price(price)
                .format(format)
                .status(status)
                .dateOfPurchase(dateOfPurchase)
                .publicationDate(publicationDate)
                .bookId(bookId)
                .rackId(rackId)
                .build();
    }

}