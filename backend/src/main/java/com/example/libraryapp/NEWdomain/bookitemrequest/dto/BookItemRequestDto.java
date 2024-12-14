package com.example.libraryapp.NEWdomain.bookitemrequest.dto;

import com.example.libraryapp.NEWdomain.bookitemrequest.model.BookItemRequestStatus;

import java.time.LocalDateTime;

public record BookItemRequestDto(
        Long id,
        LocalDateTime creationDate,
        BookItemRequestStatus status,
        Long userId,
        Long bookItemId
) { }
