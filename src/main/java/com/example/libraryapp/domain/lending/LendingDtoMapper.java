package com.example.libraryapp.domain.lending;

import com.example.libraryapp.domain.book.mapper.BookDtoMapper;
import com.example.libraryapp.domain.user.mapper.UserDtoMapper;

public class LendingDtoMapper {

    public static LendingDto map(Lending lending) {
        LendingDto dto = new LendingDto();
        dto.setId(lending.getId());
        dto.setStartTime(lending.getStartTime());
        dto.setEndTime(lending.getEndTime());
        dto.setUser(UserDtoMapper.map(lending.getUser()));
        dto.setBook(BookDtoMapper.map(lending.getBook()));
        return dto;
    }
}