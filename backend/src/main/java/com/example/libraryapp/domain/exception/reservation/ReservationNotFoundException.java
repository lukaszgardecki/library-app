package com.example.libraryapp.domain.exception.reservation;

import com.example.libraryapp.management.Message;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException() {
        super(Message.RESERVATION_NOT_FOUND);
    }

    public ReservationNotFoundException(Long id) {
        super(String.format(Message.RESERVATION_NOT_FOUND_BY_ID, id));
    }
}
