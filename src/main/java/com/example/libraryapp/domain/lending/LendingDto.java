package com.example.libraryapp.domain.lending;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.user.dto.UserDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
public class LendingDto extends RepresentationModel<LendingDto> {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private UserDto user;
    private BookDto book;
}