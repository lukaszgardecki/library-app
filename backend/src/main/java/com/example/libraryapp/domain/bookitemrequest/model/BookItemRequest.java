package com.example.libraryapp.domain.bookitemrequest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BookItemRequest {
    private Long id;
    private LocalDateTime creationDate;
    private BookItemRequestStatus status;
    private Long userId;
    private Long bookItemId;
}
