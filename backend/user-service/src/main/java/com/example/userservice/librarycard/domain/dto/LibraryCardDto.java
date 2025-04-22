package com.example.userservice.librarycard.domain.dto;

import com.example.userservice.librarycard.domain.model.LibraryCardStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class LibraryCardDto{
    private Long id;
    private String barcode;
    private LocalDateTime issuedAt;
    private LibraryCardStatus status;
    private Long userId;
}
