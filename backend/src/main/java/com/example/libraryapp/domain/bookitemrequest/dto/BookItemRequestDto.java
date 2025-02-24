package com.example.libraryapp.domain.bookitemrequest.dto;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;

import java.time.LocalDateTime;

public record BookItemRequestDto(
        Long id,
        LocalDateTime creationDate,
        BookItemRequestStatus status,
        Long userId,
        Long bookItemId
) { }
