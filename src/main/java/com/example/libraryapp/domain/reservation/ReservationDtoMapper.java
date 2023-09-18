package com.example.libraryapp.domain.reservation;

import com.example.libraryapp.domain.book.mapper.BookDtoMapper;
import com.example.libraryapp.domain.user.mapper.UserDtoMapper;

public class ReservationDtoMapper {

    public static ReservationDto map(Reservation reservation) {
        ReservationDto dto = new ReservationDto();
        dto.setId(reservation.getId());
        dto.setStartTime(reservation.getStartTime());
        dto.setEndTime(reservation.getEndTime());
        dto.setUser(UserDtoMapper.map(reservation.getUser()));
        dto.setBook(BookDtoMapper.map(reservation.getBook()));
        return dto;
    }
}