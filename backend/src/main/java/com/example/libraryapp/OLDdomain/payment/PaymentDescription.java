package com.example.libraryapp.OLDdomain.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentDescription {
    FINE_LATE_RETURN("Late return"),
    FINE_LOST_BOOK("Lost book");

    private final String description;
}
