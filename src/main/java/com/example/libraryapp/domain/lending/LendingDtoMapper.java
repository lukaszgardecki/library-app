package com.example.libraryapp.domain.lending;

import com.example.libraryapp.domain.lending.dto.LendingDto;

public class LendingDtoMapper {

    public static LendingDto map(Lending lending) {
        LendingDto dto = new LendingDto();
        dto.setId(lending.getId());
        dto.setCreationDate(lending.getCreationDate());
        dto.setDueDate(lending.getDueDate());
        dto.setReturnDate(lending.getReturnDate());
        dto.setStatus(lending.getStatus());
        dto.setMemberId(lending.getMember().getId());
        dto.setBookBarcode(lending.getBookItem().getBarcode());
        return dto;
    }
}