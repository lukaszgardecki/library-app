package com.example.libraryapp.domain.lending.dto;

import com.example.libraryapp.domain.lending.LendingStatus;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LendingDto extends RepresentationModel<LendingDto> {
    private Long id;
    private LocalDate creationDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private LendingStatus status;
    private Long memberId;
    private String bookBarcode;
}