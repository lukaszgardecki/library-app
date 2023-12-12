package com.example.libraryapp.domain.reservation;

import com.example.libraryapp.domain.book.mapper.BookMapper;
import com.example.libraryapp.domain.member.mapper.MemberDtoMapper;
import com.example.libraryapp.domain.reservation.dto.ReservationResponse;

public class ReservationDtoMapper {

    public static ReservationResponse map(Reservation reservation) {
        ReservationResponse dto = new ReservationResponse();
        dto.setId(reservation.getId());
        dto.setCreationDate(reservation.getCreationDate());
        dto.setStatus(reservation.getStatus());
        dto.setMember(reservation.getMember() != null ? MemberDtoMapper.map(reservation.getMember()) : null);
        dto.setBook(reservation.getBookItem() != null ? BookMapper.map(reservation.getBookItem().getBook()) : null);
        return dto;
    }
}