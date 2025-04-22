package com.example.libraryapp.domain.librarycard.dto;

import com.example.userservice.common.librarycard.model.LibraryCardStatus;
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
