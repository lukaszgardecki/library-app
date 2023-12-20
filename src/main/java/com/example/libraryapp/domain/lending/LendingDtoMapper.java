package com.example.libraryapp.domain.lending;

import com.example.libraryapp.domain.bookItem.mapper.BookItemMapper;
import com.example.libraryapp.domain.lending.dto.LendingDto;
import com.example.libraryapp.domain.member.mapper.MemberDtoMapper;

public class LendingDtoMapper {

    public static LendingDto map(Lending lending) {
        LendingDto dto = new LendingDto();
        dto.setId(lending.getId());
        dto.setCreationDate(lending.getCreationDate());
        dto.setDueDate(lending.getDueDate());
        dto.setReturnDate(lending.getReturnDate());
        dto.setStatus(lending.getStatus());
        dto.setMember(MemberDtoMapper.map(lending.getMember()));
        dto.setBookItem(BookItemMapper.map(lending.getBookItem()));
        return dto;
    }
}