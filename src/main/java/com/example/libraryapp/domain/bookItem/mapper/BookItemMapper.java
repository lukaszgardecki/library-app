package com.example.libraryapp.domain.bookItem.mapper;

import com.example.libraryapp.domain.bookItem.BookItem;
import com.example.libraryapp.domain.bookItem.dto.BookItemDto;

public class BookItemMapper {

    public static BookItemDto map(BookItem bookItem) {
        return BookItemDto.builder()
                .id(bookItem.getId())
                .barcode(bookItem.getBarcode())
                .isReferenceOnly(bookItem.getIsReferenceOnly())
                .borrowed(bookItem.getBorrowed())
                .dueDate(bookItem.getDueDate())
                .price(bookItem.getPrice())
                .format(bookItem.getFormat())
                .status(bookItem.getStatus())
                .dateOfPurchase(bookItem.getDateOfPurchase())
                .publicationDate(bookItem.getPublicationDate())
                .book(bookItem.getBook())
                .build();
    }

    public static BookItem map(BookItemDto dto) {
        BookItem bookItem = new BookItem();
        bookItem.setId(dto.getId());
        bookItem.setBarcode(dto.getBarcode());
        bookItem.setIsReferenceOnly(dto.getIsReferenceOnly());
        bookItem.setBorrowed(dto.getBorrowed());
        bookItem.setDueDate(dto.getDueDate());
        bookItem.setPrice(dto.getPrice());
        bookItem.setFormat(dto.getFormat());
        bookItem.setStatus(dto.getStatus());
        bookItem.setDateOfPurchase(dto.getDateOfPurchase());
        bookItem.setPublicationDate(dto.getPublicationDate());
        bookItem.setBook(dto.getBook());
        return bookItem;
    }

}
