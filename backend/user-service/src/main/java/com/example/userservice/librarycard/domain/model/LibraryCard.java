package com.example.userservice.librarycard.domain.model;

import com.example.userservice.user.domain.model.user.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class LibraryCard {
    private LibraryCardId id;
    private LibraryCardBarcode barcode;
    private IssuedDate issuedAt;
    private LibraryCardStatus status;
    private UserId userId;
}
