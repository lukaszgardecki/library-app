package com.example.libraryapp.NEWdomain.user.dto;

import com.example.libraryapp.NEWdomain.user.model.CardStatus;
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
    private CardStatus status;
}
