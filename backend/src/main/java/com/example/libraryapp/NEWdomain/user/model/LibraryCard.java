package com.example.libraryapp.NEWdomain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class LibraryCard {
    private Long id;
    private String barcode;
    private LocalDateTime issuedAt;
    private CardStatus status;
}
