package com.example.statisticsservice.infrastructure.http.mapper;

import com.example.statisticsservice.domain.model.borrower.Borrower;
import com.example.statisticsservice.domain.model.borrower.values.*;
import com.example.statisticsservice.infrastructure.http.dto.BorrowerDto;

public class BorrowerMapper {

    public static Borrower toModel(BorrowerDto dto) {
        return Borrower.builder()
                .id(new BorrowerId(dto.getId()))
                .userId(new UserId(dto.getUserId()))
                .firstName(new PersonFirstName(dto.getFirstName()))
                .lastName(new PersonLastName(dto.getLastName()))
                .loans(new LoansCount(dto.getLoans()))
                .build();
    }

    public static BorrowerDto toDto(Borrower model) {
        return BorrowerDto.builder()
                .id(model.getId().value())
                .userId(model.getUserId().value())
                .firstName(model.getFirstName().value())
                .lastName(model.getLastName().value())
                .loans(model.getLoans().value())
                .build();
    }
}
