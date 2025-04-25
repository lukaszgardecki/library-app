package com.example.requestservice.domain.dto;

import com.example.requestservice.domain.model.BookItemRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookItemRequestDto {
    private Long id;
    private LocalDateTime creationDate;
    private BookItemRequestStatus status;
    private Long userId;
    private Long bookItemId;
}
