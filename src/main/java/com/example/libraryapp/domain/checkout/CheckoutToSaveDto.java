package com.example.libraryapp.domain.checkout;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutToSaveDto {
    private Long userId;
    private Long bookId;
}