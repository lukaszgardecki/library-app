package com.example.libraryapp.domain.lending.dto;

import com.example.libraryapp.domain.bookItem.dto.BookItemDto;
import com.example.libraryapp.domain.lending.LendingStatus;
import com.example.libraryapp.domain.member.dto.MemberDto;
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
    private MemberDto member;
    private BookItemDto bookItem;
}