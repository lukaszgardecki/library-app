package com.example.libraryapp.domain.reservation;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationToSaveDto {
    private Long userId;
    private Long bookId;
}