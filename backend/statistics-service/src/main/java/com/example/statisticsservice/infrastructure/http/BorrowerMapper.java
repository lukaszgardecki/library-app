package com.example.statisticsservice.infrastructure.http;

import com.example.statisticsservice.domain.model.borrower.Borrower;
import com.example.statisticsservice.domain.model.borrower.values.*;
import com.example.statisticsservice.infrastructure.http.dto.BorrowerDto;

public class BorrowerMapper {

    public static Borrower toModel(BorrowerDto dto) {
        return Borrower.builder()
                .id(new BorrowerId(dto.getId()))
                .userId(new UserId(dto.getUserId()))
                .firstName(new PersonFirstName(dto.getFullName().split(" ")[0]))
                .lastName(new PersonLastName(dto.getFullName().split(" ")[1]))
                .loans(new LoansCount(dto.getLoans()))
                .build();
    }

    public static BorrowerDto toDto(Borrower model) {
        return BorrowerDto.builder()
                .id(model.getId().value())
                .userId(model.getUserId().value())
                .fullName("%s %s".formatted(model.getFirstName().value(), model.getLastName().value()))
                .loans(model.getLoans().value())
                .build();
    }
}
