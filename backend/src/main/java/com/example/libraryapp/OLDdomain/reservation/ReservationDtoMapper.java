package com.example.libraryapp.OLDdomain.reservation;

import com.example.libraryapp.OLDdomain.member.mapper.MemberDtoMapper;
import com.example.libraryapp.OLDdomain.reservation.dto.ReservationResponse;

public class ReservationDtoMapper {

    public static ReservationResponse map(Reservation reservation) {
        ReservationResponse dto = new ReservationResponse();
        dto.setId(reservation.getId());
        dto.setCreationDate(reservation.getCreationDate());
        dto.setStatus(reservation.getStatus());
        dto.setMember(reservation.getMember() != null ? MemberDtoMapper.map(reservation.getMember()) : null);
//        dto.setBookItem(reservation.getBookItem() != null ? BookItemMapper.map(reservation.getBookItem()) : null);
        return dto;
    }
}