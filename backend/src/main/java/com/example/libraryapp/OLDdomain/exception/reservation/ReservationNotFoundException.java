package com.example.libraryapp.OLDdomain.exception.reservation;

import com.example.libraryapp.OLDmanagement.Message;

public class ReservationNotFoundException extends RuntimeException {

    public ReservationNotFoundException() {
        super("Message.RESERVATION_NOT_FOUND.getMessage()");
    }

    public ReservationNotFoundException(Long id) {
        super("Message.RESERVATION_NOT_FOUND_ID.getMessage(id)");
    }
}
