package com.example.libraryapp.NEWdomain.bookitemrequest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BookItemRequest {
    private Long id;
    private LocalDateTime creationDate;
    private BookItemRequestStatus status;
    private Long userId;
    private Long bookItemId;
}
