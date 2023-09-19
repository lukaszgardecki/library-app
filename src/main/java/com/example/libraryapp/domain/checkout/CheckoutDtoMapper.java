package com.example.libraryapp.domain.checkout;

import com.example.libraryapp.domain.book.mapper.BookDtoMapper;
import com.example.libraryapp.domain.user.mapper.UserDtoMapper;

public class CheckoutDtoMapper {

    public static CheckoutDto map(Checkout checkout) {
        CheckoutDto dto = new CheckoutDto();
        dto.setId(checkout.getId());
        dto.setStartTime(checkout.getStartTime());
        dto.setEndTime(checkout.getEndTime());
        dto.setUser(UserDtoMapper.map(checkout.getUser()));
        dto.setBook(BookDtoMapper.map(checkout.getBook()));
        return dto;
    }
}