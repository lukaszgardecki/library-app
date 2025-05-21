package com.example.userservice.domain.model.librarycard;

import com.example.userservice.domain.model.librarycard.values.IssuedDate;
import com.example.userservice.domain.model.librarycard.values.LibraryCardBarcode;
import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.domain.model.librarycard.values.LibraryCardStatus;
import com.example.userservice.domain.model.user.values.UserId;
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
