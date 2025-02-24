package com.example.libraryapp.domain.fine.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FineCardDetailsDto {
    private String cardNumber;
    private String cardHolderName;
    private String expirationDate; // Format: MM/YY
    private String cvv;
}
